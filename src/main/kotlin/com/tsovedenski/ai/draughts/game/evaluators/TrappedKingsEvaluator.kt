package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.state.State
import com.tsovedenski.ai.draughts.game.elements.Color

/**
 * Created by Tsvetan Ovedenski on 13/05/17.
 */
class TrappedKingsEvaluator (val weight: Int = 3): Evaluator {
    override fun evaluate(state: State, color: Color): Int {
        val kings = state.points(color).filter { state[it]!!.piece!!.king }

        return kings.map { state.moves(it).size }.filter { it == 0 }.size * weight
    }
}