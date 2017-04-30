package com.tsovedenski.ai.draughts

import com.tsovedenski.ai.draughts.game.Draughts
import com.tsovedenski.ai.draughts.game.Game
import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.players.ConsolePlayer
import com.tsovedenski.ai.draughts.players.Player

/**
 * Created by Tsvetan Ovedenski on 30/04/17.
 */
fun main(args: Array<String>) {
//    val game = Game(6, 7, 4)
    val game = Draughts()
    game.listener = Listener

    val p1 = ConsolePlayer("John", Color.White)
    val p2 = ConsolePlayer("Bob", Color.Black)
//    val p2 = MinimaxPlayer(depth = 3, color = Color.Black)
//    val p2 = AlphabetaPlayer(depth = 3, color = Color.Black)

//    val p1 = MinimaxPlayer(depth = 3, color = Color.Red)
//    val p2 = AlphabetaPlayer(depth = 1, color = Color.Black)

    game.play(p1, p2)
}

object Listener: Game.ActionListener {
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