package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
object PersonalEvaluator: Evaluator {

    override fun evaluate(state: State, color: Color): Int {
        val pieces = state.pieces(color).map { it.piece!! }

        return (pieces.filter { it.king }.size * 1400 + pieces.filter { !it.king }.size * 1000)
    }
}