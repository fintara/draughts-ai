package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.state.State

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 *
 * Combines several evaluators and sums their result.
 */
class MultiEvaluator(vararg val evaluators: Evaluator) : Evaluator {
    override fun evaluate(state: State, color: Color) = evaluators.map { it.evaluate(state, color) }.sum()
}