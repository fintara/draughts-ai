package com.tsovedenski.ai.draughts.game

import com.tsovedenski.ai.draughts.game.elements.*
import com.tsovedenski.ai.draughts.players.Player
import java.util.*

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */

data class State (private val board: Array<Array<Cell>>, val size: Int, val pieces: Int) {
    constructor(size: Int, pieces: Int): this(generate(size, pieces), size, pieces)

    init {
        if (pieces % (size / 2) != 0) {
            throw IllegalArgumentException("State with size $size cannot have $pieces pieces")
        }
    }

    fun valid(move: Move): Boolean {
        if (!board[move.from].allowed || !board[move.to].allowed) {
            return false
        }

        if (board[move.from].piece == null) {
            return false
        }

        if (board[move.to].piece != null) {
            return false
        }

        if (board[move.from].piece!!.color != move.player.color) {
            return false
        }

        return true
    }

    fun apply(move: Move): State {
        if (!valid(move)) {
            throw RuntimeException("Illegal move $move")
        }

        val cloned = copy(board = board.map { it.clone() }.toTypedArray())

        val piece = cloned.board[move.from].piece
        cloned.board[move.from] = cloned.board[move.from].copy(piece = null)

        cloned.board[move.to] = cloned.board[move.to].copy(piece = piece)

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

        board.forEachIndexed { index, row ->
            builder.appendln("${String.format("%1$3d", index)}| ${row.map {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as State
        if (!Arrays.equals(board, other.board)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(board)
    }

    companion object {
        var cnt = 0

        internal operator fun Array<Array<Cell>>.get(p: Point) = this[p.row][p.col]
        internal operator fun Array<Array<Cell>>.set(p: Point, c: Cell) {
            this[p.row][p.col] = c
        }

        private fun generate(size: Int, pieces: Int) =
                (0..size-1)
                        .map { row ->
                            (0..size-1).map { col ->
                                val allowed = (row * size + col + 1) and 1 == row and 1
                                val piece = when {
                                    allowed && row < pieces / (size / 2) -> Piece(color = Color.Black)
                                    allowed && row >= size - pieces / (size / 2) -> Piece(color = Color.White)
                                    else -> null
                                }
                                Cell(piece = piece, allowed = allowed)
                            }.toTypedArray()
                        }
                        .toTypedArray()
    }
}