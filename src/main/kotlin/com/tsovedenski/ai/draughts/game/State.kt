package com.tsovedenski.ai.draughts.game

import com.tsovedenski.ai.draughts.game.elements.Cell
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.elements.Point
import com.tsovedenski.ai.draughts.players.Player
import org.slf4j.LoggerFactory

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */

data class State (private val board: LinkedHashMap<Point, Cell>, val size: Int, val pieces: Int) {
    constructor(size: Int, pieces: Int): this(generate(size, pieces), size, pieces)

    init {
        if (pieces % (size / 2) != 0) {
            throw IllegalArgumentException("State with size $size cannot have $pieces pieces")
        }
    }

    fun valid(move: Move): Boolean {
        // points exist
        if (!board.containsKey(move.from) || !board.containsKey(move.to)) {
            log.info("$move contains point out of boundaries")
            return false
        }

        // move is diagonal
        if (Math.abs(move.from.row - move.to.row) != Math.abs(move.from.col - move.to.col)) {
            log.info("$move is not diagonal")
            return false
        }

        // points are allowed
        if (!board[move.from]!!.allowed || !board[move.to]!!.allowed) {
            log.info("$move contains point on not allowed square")
            return false
        }

        // from has piece and to doesn't
        if (board[move.from]!!.piece == null || board[move.to]!!.piece != null) {
            log.info("$move contains point without piece (from) or existing piece (to)")
            return false
        }

        // from has player's piece
        if (board[move.from]!!.piece!!.color != move.player.color) {
            log.info("$move contains point from with not player's piece")
            return false
        }

        log.info("$move is valid")
        return true
    }

    fun apply(move: Move): State {
        if (!valid(move)) {
            log.error("Tried to apply illegal move $move")
            throw RuntimeException("Illegal move $move")
        }

        val cloned = copy(board = LinkedHashMap(board))

        val piece = cloned.board[move.from]!!.piece
        cloned.board[move.from] = cloned.board[move.from]!!.copy(piece = null)

        cloned.board[move.to] = cloned.board[move.to]!!.copy(piece = piece)

        return cloned
    }

    fun winner(player: Player): Player? {
        if (cnt++ == 3) {
            return player
        }
        return null
    }

    override fun toString(): String {
        val padding = " --"
        val divider = padding + "+---".repeat(size+1).substring(0, (size+1)*4-3)

        val builder = StringBuilder()

        builder.append("col| ${(0..Math.min(9, size-1)).toList().joinToString(" | ")} |")
        if (size > 10) {
            builder.append(" ${(10..size-1).toList().joinToString("| ")}|")
        }
        builder.appendln()

        builder.appendln("row${divider.substring(3)}")

        for (i in 0..size*size-1 step size) {
            val row = board.keys.toList().subList(i, i+size)
            builder.appendln("${String.format("%1$3d", i / size)}| ${row.map { point ->
                val it = board[point]!!
                when (it.allowed) {
                    true -> it.piece?.color?.char ?: '░'
                    else -> ' '
                }
            }.joinToString(" | ")} |")
            builder.appendln(divider)
        }

        builder.append("col| ${(0..Math.min(9, size-1)).toList().joinToString(" | ")} |")
        if (size > 10) {
            builder.append(" ${(10..size-1).toList().joinToString("| ")}|")
        }
        builder.appendln()

        return builder.toString()
    }

    companion object {
        var cnt = 0

        private val log = LoggerFactory.getLogger(State::class.java)

        private fun generate(size: Int, pieces: Int) = LinkedHashMap<Point, Cell>().apply {
            (0..size-1).forEach { row ->
                            (0..size-1).forEach { col ->
                                val point = Point(row, col)
                                val cell  = Cell.fromPosition(size, pieces, row, col)

                                put(point, cell)
                            }
                        }
        }
    }
}