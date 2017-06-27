package com.tsovedenski.ai.draughts.players.ai

import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.evaluators.Evaluator
import com.tsovedenski.ai.draughts.game.evaluators.PiecesCountEvaluator
import com.tsovedenski.ai.draughts.game.state.State
import org.slf4j.LoggerFactory

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
class AlphabetaPlayer(val depth: Int, color: Color, evaluator: Evaluator) : ArtificialPlayer(color, evaluator) {

    override fun move(state: State): Move? {
        val start = System.currentTimeMillis()

        val result = alphabeta(state, color, depth, Int.MIN_VALUE, Int.MAX_VALUE)

        val end = System.currentTimeMillis()
        log.warn("Move decision took ${end - start} ms")

        return result.move
    }

    private fun alphabeta(state: State, color: Color, depth: Int, alpha: Int, beta: Int): ScoreMovePair {
        val states = state.moves(color)
                .map { StateMovePair(state = state.apply(it), move = it) }
                .sortedBy { preevaluate(it.state, color) * when (color == this.color) {
                    true -> 1
                    else -> -1
                } }

        if (depth == 0 || states.isEmpty()) {
            val score = evaluate(state, color) * when (color == this.color) {
                true -> 1
                else -> -1
            }
//            val score = evaluate(state, color.opposite()) - evaluate(state, color)

            return ScoreMovePair(score)
        }

        if (color == this.color) {
            var currentAlpha = DEFAULT_SCORE_PAIR.copy(score = alpha)
            states.forEach { (child, move) ->
                val result = alphabeta(child, color.opposite(), depth - 1, currentAlpha.score, beta)

                if (result.score > currentAlpha.score) {
                    currentAlpha = result.copy(move = move)
                }

                if (currentAlpha.score >= beta) {
                    return currentAlpha
                }
            }
            return currentAlpha
        }

        var currentBeta = DEFAULT_SCORE_PAIR.copy(score = beta)
        states.forEach { (child, move) ->
            val result = alphabeta(child, color.opposite(), depth - 1, alpha, currentBeta.score)

            if (result.score < currentBeta.score) {
                currentBeta = result.copy(move = move)
            }

            if (alpha >= currentBeta.score) {
                return currentBeta
            }
        }
        return currentBeta
    }

    private fun preevaluate(state: State, color: Color): Int {
        val my = piecesEvaluator.evaluate(state, color)
        val op = piecesEvaluator.evaluate(state, color.opposite())

        return my - op
    }

    companion object {
        private val DEFAULT_SCORE_PAIR = ScoreMovePair(0)
        private data class ScoreMovePair(val score: Int, val move: Move? = null)
        private data class StateMovePair(val state: State, val move: Move? = null)
        private val log = LoggerFactory.getLogger(AlphabetaPlayer::class.java)
        private val piecesEvaluator = PiecesCountEvaluator()
    }
}