package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.state.State
import com.tsovedenski.ai.draughts.game.elements.Color

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
class MultiEvaluator (vararg val evaluators: Evaluator): Evaluator {

    override fun evaluate(state: State, color: Color): Int {
        return evaluators.map { it.evaluate(state, color) }.sum()
    }
}