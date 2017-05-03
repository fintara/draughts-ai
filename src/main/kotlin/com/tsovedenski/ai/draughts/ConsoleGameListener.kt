package com.tsovedenski.ai.draughts

import com.tsovedenski.ai.draughts.game.Game
import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.players.Player

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
object ConsoleGameListener : Game.ActionListener {
    override fun beforeStart() = println("Starting Draughts")

    override fun beforeMove(state: State, player: Player) {
//        println("\u001Bc")
        println(state)
    }

    override fun afterMove(state: State, player: Player, move: Move) {
        println("$player chooses: $move")
        println("------------------ END OF MOVE ------------------")
    }

    override fun afterFinish(state: State, winner: Player?) {
        println(state)
        println(when(winner) {
            is Player -> "Congratulations, winner is $winner"
            else -> "It's a tie!"
        })
    }
}