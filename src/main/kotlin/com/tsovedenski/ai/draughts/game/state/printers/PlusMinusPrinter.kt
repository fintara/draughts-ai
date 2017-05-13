package com.tsovedenski.ai.draughts.game.state.printers

import com.tsovedenski.ai.draughts.game.state.State

/**
 * Created by Tsvetan Ovedenski on 13/05/17.
 */
object PlusMinusPrinter: Printer {
    override fun print(state: State): String {
        val padding = " --"
        val divider = padding + "+---".repeat(state.size+1).substring(0, (state.size+1)*4-3) + padding.reversed()

        val builder = StringBuilder()

        builder.append("col| ${(0..Math.min(9, state.size-1)).toList().joinToString(" | ")} |col")
        if (state.size > 10) {
            builder.append(" ${(10..state.size-1).toList().joinToString("| ")}|")
        }
        builder.appendln()

        builder.appendln("row${divider.substring(3,divider.length-3)}row")

        for (i in 0..state.size*state.size-1 step state.size) {
            val row = state.board.keys.toList().subList(i, i+state.size)
            val rowIndex = i / state.size
            builder.appendln("${String.format("%1$3d", rowIndex)}│${row.map { point ->
                val it = state.board[point]!!
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

        builder.append("col| ${(0..Math.min(9, state.size-1)).toList().joinToString(" | ")} |col")
        if (state.size > 10) {
            builder.append(" ${(10..state.size-1).toList().joinToString("| ")}|")
        }
        builder.appendln()

        return builder.toString()
    }
}