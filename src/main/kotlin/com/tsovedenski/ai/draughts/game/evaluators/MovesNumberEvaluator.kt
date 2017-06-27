package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.state.State

/**
 * Created by Tsvetan Ovedenski on 09/05/17.
 *
 * Number of possible moves for all player's pieces.
 */
class MovesNumberEvaluator(val factor: Int) : Evaluator {

    override fun evaluate(state: State, color: Color): Int {
        return state.moves(color).size * factor
    }

}