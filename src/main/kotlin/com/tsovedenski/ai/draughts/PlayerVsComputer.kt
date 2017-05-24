package com.tsovedenski.ai.draughts

import com.tsovedenski.ai.draughts.game.Draughts
import com.tsovedenski.ai.draughts.game.Game
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.evaluators.*
import com.tsovedenski.ai.draughts.game.state.State
import com.tsovedenski.ai.draughts.players.*
import com.tsovedenski.ai.draughts.players.ai.AlphabetaPlayer

/**
 * Created by Tsvetan Ovedenski on 30/04/17.
 */
fun main(args: Array<String>) {

    val game = Draughts().apply {
        listener = ConsoleGameListener
    }

    val players = mutableListOf<Player>()

    players.add(ConsolePlayer("Tsvetan", Color.White))

    val multi = MultiEvaluator(
            PiecesCountEvaluator(regularWeight = 28, kingWeight = 56),
            TrappedKingsEvaluator(weight = -14),
            MovesNumberEvaluator(factor = 3),
            AttackerEvaluator(weight = 20),
            PathToKingEvaluator(weight = 8)
    )
    players.add(AlphabetaPlayer(depth = 5, color = Color.Black, evaluator = multi))

    game.play(*players.toTypedArray())
}

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