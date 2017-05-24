package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.state.State
import com.tsovedenski.ai.draughts.game.elements.Color

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
object KeepCloseEvaluator: Evaluator {

    override fun evaluate(state: State, color: Color): Int {
        val pieces = state.points(color)

        var distance = 0.0

        pieces.forEach { p1 ->
            pieces.forEach { p2 ->
                if (p1 != p2) {
                    val dr = Math.abs(p1.row - p2.row)
                    val dc = Math.abs(p1.col - p2.col)

                    distance += dr*dr + dc*dc
                }
            }
        }

        if (distance == 0.0) {
            return 0
        }

        distance /= pieces.size

        return (100.0 / distance).toInt()
    }
}