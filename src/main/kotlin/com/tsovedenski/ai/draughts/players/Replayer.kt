package com.tsovedenski.ai.draughts.players

import com.tsovedenski.ai.draughts.game.state.State
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
class Replayer (override val color: Color, val moves: List<Move>, var offset: Int, val step: Int): Player {

    constructor(color: Color, moves: List<Move>, offset: Int): this(color, moves, offset, 2)

    override fun move(state: State): Move? {
        if (offset >= moves.size) {
            return null
        }

        val move = moves[offset]
        offset += step

        return move
    }
}