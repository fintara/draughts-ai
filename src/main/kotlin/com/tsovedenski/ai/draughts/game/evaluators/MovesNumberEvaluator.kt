package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.state.State
import com.tsovedenski.ai.draughts.game.elements.Color

/**
 * Created by Tsvetan Ovedenski on 09/05/17.
 */
object MovesNumberEvaluator: Evaluator {

    override fun evaluate(state: State, color: Color): Int {
        return state.moves(color).size
    }

}