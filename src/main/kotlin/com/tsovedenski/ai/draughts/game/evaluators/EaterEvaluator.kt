package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.state.State

/**
 * Created by Tsvetan Ovedenski on 13/05/17.
 */
class EaterEvaluator (val weight: Int): Evaluator {
    override fun evaluate(state: State, color: Color): Int {
        return -state.count(color.opposite()) * weight
    }
}