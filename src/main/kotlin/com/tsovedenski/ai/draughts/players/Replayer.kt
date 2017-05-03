package com.tsovedenski.ai.draughts.players

import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
class Replayer (color: Color, val moves: List<Move>, var pointer: Int): Player("Replayer #${++id}", color) {

    override fun move(state: State): Move? {
        if (pointer >= moves.size) {
            return null
        }

        val move = moves[pointer]
        pointer += 2

        return move
    }

    companion object {
        private var id = 0
    }

}