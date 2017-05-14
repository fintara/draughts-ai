package com.tsovedenski.ai.draughts.players.ai

import com.tsovedenski.ai.draughts.game.state.State
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.evaluators.Evaluator
import com.tsovedenski.ai.draughts.game.evaluators.PiecesCountEvaluator
import org.slf4j.LoggerFactory

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
class AlphabetaPlayer (val depth: Int, color: Color, evaluator: Evaluator): ArtificialPlayer(color, evaluator) {

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

            return ScoreMovePair(score)
        }

        var nextDepth = depth - 1

//        if (states.size < state.size / 2 - 1) {
////            log.warn("At depth $depth states are ${states.size} - increasing depth with 1")
//            nextDepth++
//        }

        if (color == this.color) {
            var currentAlpha = DEFAULT_SCORE_PAIR.copy(score = alpha)
            states.forEach { (child, move) ->
                val result = alphabeta(child, color.opposite(), nextDepth, currentAlpha.score, beta)

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
            val result = alphabeta(child, color.opposite(), nextDepth, alpha, currentBeta.score)

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
        val my = PiecesCountEvaluator().evaluate(state, color)
        val op = PiecesCountEvaluator().evaluate(state, color.opposite())

        return my - op;
//        val opponentLoses = state.pieces - state.count(color.opposite())
//
//        val pieces = state.pieces(color)
//        val ownPieces = pieces.filter { !it.piece!!.king }.size * 1
//        val ownKings = pieces.filter { it.piece!!.king }.size * 2
//
//        return opponentLoses + ownPieces + ownKings
    }

    companion object {
        private val DEFAULT_SCORE_PAIR = ScoreMovePair(0)
        private data class ScoreMovePair(val score: Int, val move: Move? = null)
        private data class StateMovePair(val state: State, val move: Move? = null)
        private val log = LoggerFactory.getLogger(AlphabetaPlayer::class.java)
    }
}