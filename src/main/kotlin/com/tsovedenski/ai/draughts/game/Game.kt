package com.tsovedenski.ai.draughts.game

import com.tsovedenski.ai.draughts.players.Player

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
interface Game {
    fun play(vararg players: Player)

    interface ActionListener {
        fun beforeStart()
        fun beforeMove(state: State, player: Player)
        fun afterMove(state: State, player: Player)
        fun afterFinish(winner: Player?)
    }
}