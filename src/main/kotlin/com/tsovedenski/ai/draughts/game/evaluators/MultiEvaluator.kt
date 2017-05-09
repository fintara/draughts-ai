package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
class MultiEvaluator (vararg val evaluators: Evaluator): Evaluator {

    override fun evaluate(state: State, color: Color): Int {
        val scores = mutableListOf<Int>()

        evaluators.forEach { evaluator ->
            scores.add(evaluator.evaluate(state, color))
        }

        return scores.sum()
    }
}