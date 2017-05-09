package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color

/**
 * Created by Tsvetan Ovedenski on 09/05/17.
 */
object BlockedMovesEvaluator: Evaluator {

    val weight = 2

    override fun evaluate(state: State, color: Color): Int {
        return state.points(color.opposite()).map { state.moves(it).size }.filter { it == 0 }.size * weight
    }

}