package com.tsovedenski.ai.draughts.players

import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.evaluators.Evaluator

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
class AlphabetaPlayer (val depth: Int, color: Color, evaluator: Evaluator): ArtificialPlayer(color, evaluator) {

    override fun move(state: State): Move? {
        val result = alphabeta(state, color, depth, Int.MIN_VALUE, Int.MAX_VALUE)
        return result.move
    }

    private fun alphabeta(state: State, color: Color, depth: Int, alpha: Int, beta: Int): ScoreMovePair {
        if (depth == 0) {
            val stateScore = evaluate(state, color) * when (color == this.color) {
                true -> 1
                else -> -1
            }
            return ScoreMovePair(stateScore)
        }

        val states = state.moves(color)
                .map { StateMovePair(state.apply(it), it) }

        if (this.color == color) {
            var currentAlpha = DEFAULT_SCORE_PAIR.copy(score = alpha)
            states.forEach { (state, move) ->
                val result = alphabeta(state, color.opposite(), depth - 1, alpha, beta) // or other player
                if (result.score > currentAlpha.score) currentAlpha = result.copy(move = move)
                if (currentAlpha.score >= beta) return currentAlpha
            }
            return currentAlpha
        }

        var currentBeta = DEFAULT_SCORE_PAIR.copy(score = beta)
        states.forEach { (state, move) ->
            val result = alphabeta(state, color.opposite(), depth - 1, alpha, beta) // or other player
            if (result.score < currentBeta.score) currentBeta = result.copy(move = move)
            if (alpha >= currentBeta.score) return currentBeta
        }
        return currentBeta
    }

    companion object {
        private val DEFAULT_SCORE_PAIR = ScoreMovePair(0)
        private data class ScoreMovePair(val score: Int, val move: Move? = null)
        private data class StateMovePair(val state: State, val move: Move? = null)
    }
}