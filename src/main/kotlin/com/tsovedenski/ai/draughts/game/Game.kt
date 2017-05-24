package com.tsovedenski.ai.draughts.game

import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.state.State
import com.tsovedenski.ai.draughts.players.Player

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
interface Game {
    fun play(vararg players: Player)

    interface ActionListener {
        fun beforeStart()
        fun beforeMove(state: State, player: Player)
        fun afterMove(state: State, player: Player, move: Move)

        /**
         * Final state with possible winner (null if tie)
         */
        fun afterFinish(state: State, winner: Player?)
    }
}