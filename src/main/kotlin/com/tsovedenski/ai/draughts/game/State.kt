package com.tsovedenski.ai.draughts.game

import com.tsovedenski.ai.draughts.game.elements.*
import com.tsovedenski.ai.draughts.players.Player
import java.util.*
import kotlin.collections.LinkedHashMap

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
        if (!board.containsKey(move.from) || !board.containsKey(move.to)) {
            return false
        }

        if (!board[move.from]!!.allowed || !board[move.to]!!.allowed) {
            return false
        }

        if (board[move.from]!!.piece == null) {
            return false
        }

        if (board[move.to]!!.piece != null) {
            return false
        }

        if (board[move.from]!!.piece!!.color != move.player.color) {
            return false
        }

        return true
    }

    fun apply(move: Move): State {
        if (!valid(move)) {
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