package com.tsovedenski.ai.draughts.game

import com.tsovedenski.ai.draughts.game.elements.Cell
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.elements.Piece
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

    fun valid(move: Move, player: Player): Boolean {
        return true
    }

    fun apply(move: Move): State {
        return State(8, 12)
    }

    fun winner(player: Player): Player? {
        return player
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