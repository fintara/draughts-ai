package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.state.State
import com.tsovedenski.ai.draughts.game.elements.Color

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
object DifferenceEvaluator: Evaluator {

    override fun evaluate(state: State, color: Color): Int {
        val own = state.count(color)
        val opp = state.count(color.opposite())

        return own - opp
    }
}