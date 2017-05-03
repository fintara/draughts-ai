package com.tsovedenski.ai.draughts.players

import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.evaluators.Evaluator

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
class MinimaxPlayer (val depth: Int, color: Color, evaluator: Evaluator): ArtificialPlayer(color, evaluator) {

    override fun move(state: State): Move {
        var result: ScoreMovePair

        do {
            result = minimax(state, color, depth)
        } while (result.move == null)

        return result.move!!;
    }

    private fun minimax(state: State, color: Color, depth: Int): ScoreMovePair {
        if (depth == 0) {
            val stateScore = evaluate(state, color) * when (color == this.color) {
                true -> 1
                else -> -1
            }
            return ScoreMovePair(stateScore)
        }

        val scores = state.moves(color)
                .map { StateMovePair(state.apply(it), it) }
                .map { ScoreMovePair(minimax(it.state, color.opposite(), depth - 1).score, it.move) }

        if (this.color == color) {
            return scores.maxBy { it.score } ?: DEFAULT_SCORE_PAIR
        }

        return scores.minBy { it.score } ?: DEFAULT_SCORE_PAIR
    }

    companion object {
        private val DEFAULT_SCORE_PAIR = ScoreMovePair(0)
        private data class ScoreMovePair(val score: Int, val move: Move? = null)
        private data class StateMovePair(val state: State, val move: Move? = null)
    }
}