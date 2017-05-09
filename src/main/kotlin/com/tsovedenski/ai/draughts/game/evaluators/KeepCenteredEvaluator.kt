package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color

/**
 * Created by Tsvetan Ovedenski on 09/05/17.
 */
object KeepCenteredEvaluator: Evaluator {
    override fun evaluate(state: State, color: Color): Int {
        val center = state.size / 2.0
        val pieces = state.points(color)
        var distance = 0.0

        pieces.forEach { p ->
            val dr = Math.abs(p.row - center)
            val dc = Math.abs(p.col - center)

            distance += dr*dr + dc*dc
        }

        return (10 / distance).toInt()
    }
}