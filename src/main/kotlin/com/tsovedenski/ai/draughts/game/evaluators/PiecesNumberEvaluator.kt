package com.tsovedenski.ai.draughts.game.evaluators

import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color

/**
 * Created by Tsvetan Ovedenski on 09/05/17.
 */
object PiecesNumberEvaluator: Evaluator {

    val regularWeight = 1
    val kingWeight = 2

    override fun evaluate(state: State, color: Color): Int {
        val pieces = state.pieces(color).filter { it.piece != null }.map { it.piece!! }

        return (pieces.filter { !it.king }.size * regularWeight
              + pieces.filter {  it.king }.size * kingWeight)
    }
}