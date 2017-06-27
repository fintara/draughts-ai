package com.tsovedenski.ai.draughts.players

import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.state.State

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
class Replayer(override val color: Color, val moves: List<Move>, var offset: Int, val step: Int = 2) : Player {

    override fun move(state: State): Move? {
        if (offset >= moves.size) {
            return null
        }

        val move = moves[offset]
        offset += step

        return move
    }
}