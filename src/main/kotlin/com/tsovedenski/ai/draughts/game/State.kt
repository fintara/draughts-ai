package com.tsovedenski.ai.draughts.game

import com.tsovedenski.ai.draughts.game.elements.Cell
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.elements.Point
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

    fun valid(move: Move, color: Color): Boolean {
        if (!valid(move)) {
            return false
        }

        // from has player's piece
        if (board[move.from]!!.piece!!.color != color) {
            log.info("$move contains point (from) with not player's piece")
            return false
        }

        return true
    }

    fun valid(move: Move): Boolean {
        val from = move.from
        val to   = move.to

        log.trace("$move checking validity")

        // points exist
        if (!board.containsKey(from) || !board.containsKey(to)) {
            log.info("$move contains point out of boundaries")
            return false
        }

        // move is diagonal
        if (Math.abs(from.row - to.row) != Math.abs(from.col - to.col)) {
            log.info("$move is not diagonal")
            return false
        }

        // points are allowed
        if (!board[from]!!.allowed || !board[to]!!.allowed) {
            log.info("$move contains point on not allowed square")
            return false
        }

        // from has piece and to doesn't
        if (board[from]!!.piece == null || board[to]!!.piece != null) {
            log.info("$move contains point without piece (from) or existing piece (to)")
            return false
        }

        val diagonal = from.diagonal(to)
        if (diagonal.isNotEmpty()) {
            var opponentsCount = 0
            var emptyCnt = 0
            diagonal.forEach {
                if (board[it]!!.piece == null) {
                    emptyCnt++
                } else if (board[it]!!.piece!!.color == board[from]!!.piece!!.color) {
                    log.info("$move goes through invalid diagonal (own pieces)")
                    return false
                } else {
                    opponentsCount++
                }
            }
            if (opponentsCount - 1 != emptyCnt) {
                log.info("$move goes through invalid diagonal (empty spaces)")
                return false
            }
        }

        val fromPiece = board[from]!!.piece!!
        if (!fromPiece.king) {
            // white can go only up (row--), black can go only down (row++)
            if ((fromPiece.color == Color.White && from.row - to.row < 0) ||
                 fromPiece.color == Color.Black && from.row - to.row > 0) {

                log.info("$move goes backwards (from piece is not king)")
                return false
            }
        }

        return true
    }

    fun moves(p: Point): List<Point> {
        log.info("Generating moves for point $p")
        val lists = mutableListOf<List<Point>>()

        for (i in 1..size/2) {
            lists.add(listOf(p + Point(i, i), p + Point(i, -i), p + Point(-i, -i), p + Point(-i, i))
                .filter { valid(Move(from = p, to = it)) })
        }

        val list = lists.flatten()
        log.info("Point $p has ${list.size} move(s)")

        return list
    }

    fun apply(move: Move): State {
        if (!valid(move)) {
            log.error("Tried to apply illegal move $move")
            throw RuntimeException("Illegal move $move")
        }

        val cloned = copy(board = LinkedHashMap(board))

        // move the piece
        val piece = cloned.board[move.from]!!.piece!!
        cloned.board[move.from] = cloned.board[move.from]!!.copy(piece = null)
        cloned.board[move.to] = cloned.board[move.to]!!.copy(piece = piece)

        // remove opponents pieces on the path
        val diagonal = move.from.diagonal(move.to)
        diagonal.forEach { p ->
            log.info("Going through intermediate point $p")
            if (cloned.board[p]!!.piece?.color == cloned.board[move.to]!!.piece!!.color.opposite()) {
                log.info("Point $p has opponent's piece, removing it")
                cloned.board[p] = cloned.board[p]!!.copy(piece = null)
            }
        }

        // promote to king
        if ((move.to.row == 0        && piece.color == Color.White) ||
            (move.to.row == size - 1 && piece.color == Color.Black)) {

            log.info("Promoting $piece at ${move.to} to king")
            cloned.board[move.to] = cloned.board[move.to]!!.copy(piece = piece.copy(king = true))
        }

        return cloned
    }

    fun winner(color: Color): Color? {
        if (cnt++ == 6) {
            return color
        }
        return null
    }

    operator fun get(p: Point) = board[p]

    override fun toString(): String {
        val padding = " --"
        val divider = padding + "+---".repeat(size+1).substring(0, (size+1)*4-3) + padding.reversed()

        val builder = StringBuilder()

        builder.append("col| ${(0..Math.min(9, size-1)).toList().joinToString(" | ")} |col")
        if (size > 10) {
            builder.append(" ${(10..size-1).toList().joinToString("| ")}|")
        }
        builder.appendln()

        builder.appendln("row${divider.substring(3,divider.length-3)}row")

        for (i in 0..size*size-1 step size) {
            val row = board.keys.toList().subList(i, i+size)
            val rowIndex = i / size
            builder.appendln("${String.format("%1$3d", rowIndex)}│${row.map { point ->
                val it = board[point]!!
                when (it.allowed) {
                    true -> when (it.piece?.color?.char) {
                        null -> "░░░"
                        else -> " ${it.piece.color.char} "
                    }
                    else -> "   "
                }
            }.joinToString("│")}│$rowIndex")
            builder.appendln(divider)
        }

        builder.append("col| ${(0..Math.min(9, size-1)).toList().joinToString(" | ")} |col")
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