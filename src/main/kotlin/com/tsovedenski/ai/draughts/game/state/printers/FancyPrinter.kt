package com.tsovedenski.ai.draughts.game.state.printers

import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.state.State

/**
 * Created by Tsvetan Ovedenski on 13/05/17.
 */
object FancyPrinter: Printer {
    override fun print(state: State): String {
        val vd = "┃"
        val vdl = "┊"
        val vdo = "║"
        val padding = "$vdo┈┈┈"
        val divider = padding + "╋━━━".repeat(state.size+1).substring(0, (state.size+1)*4-3) + padding.reversed()
        val dividertop = padding + "╈━━━".repeat(state.size+1).substring(0, (state.size+1)*4-3) + padding.reversed()
        val dividerbottom = padding + "╇━━━".repeat(state.size+1).substring(0, (state.size+1)*4-3) + padding.reversed()

        val builder = StringBuilder()

        builder.appendln("╔" + "═".repeat(state.size * 4 + 7) + "╗")

        builder.append("${vdo}col$vdl ${(0..Math.min(9, state.size-1)).toList().joinToString(" $vdl ")} ${vdl}col$vdo")
        if (state.size > 10) {
            builder.append(" ${(10..state.size-1).toList().joinToString("$vdl ")}$vdl")
        }
        builder.appendln()

        builder.appendln("${vdo}row╆${dividertop.substring(5,dividertop.length-5)}╅row$vdo")

        for (i in 0..state.size*state.size-1 step state.size) {
            val row = state.board.keys.toList().subList(i, i+state.size)
            val rowIndex = i / state.size
            builder.appendln("$vdo${String.format("%1$3d", rowIndex)}$vd${row.map { point ->
                val it = state.board[point]!!
                when (it.allowed) {
                    true -> when (it.piece?.color?.char) {
                        null -> "░░░"
                        else -> " ${when (it.piece.king) {
                            true -> when (it.piece.color) {
                                Color.Black -> '♚'
                                else -> '♔'
                            }
                            else -> it.piece.color.char
                        }} "
                    }
                    else -> "   "
                }
            }.joinToString(vd)}$vd${String.format("%-3d", rowIndex)}$vdo")
            if (rowIndex < state.size - 1) {
                builder.appendln(divider)
            } else {
                builder.appendln(dividerbottom)
            }
        }

        builder.append("${vdo}col$vdl ${(0..Math.min(9, state.size-1)).toList().joinToString(" $vdl ")} ${vdl}col$vdo")
        if (state.size > 10) {
            builder.append(" ${(10..state.size-1).toList().joinToString("$vdl ")}$vdl")
        }
        builder.appendln()

        builder.appendln("╚" + "═".repeat(state.size * 4 + 7) + "╝")

        return builder.toString()
    }
}