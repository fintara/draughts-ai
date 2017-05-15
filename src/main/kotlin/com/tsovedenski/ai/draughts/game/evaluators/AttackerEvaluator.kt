package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.state.State

/**
 * Created by Tsvetan Ovedenski on 13/05/17.
 */
class AttackerEvaluator(val weight: Int): Evaluator {

    companion object {
        val counter = PiecesCountEvaluator(regularWeight = 1, kingWeight = 2)
    }

    override fun evaluate(state: State, color: Color): Int {
        return - counter.evaluate(state, color.opposite()) * weight
    }
}