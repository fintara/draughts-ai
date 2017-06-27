package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.state.State

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 *
 * The difference of number of pieces (my - opponent).
 */
object DifferenceEvaluator : Evaluator {

    override fun evaluate(state: State, color: Color): Int {
        val own = state.count(color)
        val opp = state.count(color.opposite())

        return own - opp
    }
}