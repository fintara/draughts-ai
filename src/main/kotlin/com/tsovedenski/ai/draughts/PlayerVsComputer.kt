package com.tsovedenski.ai.draughts

import com.tsovedenski.ai.draughts.game.Draughts
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.evaluators.*
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

    players.add(ConsolePlayer("John", Color.White))

    val multi1 = MultiEvaluator(
            PiecesCountEvaluator(regularWeight = 30, kingWeight = 40),
            TrappedKingsEvaluator(weight = -10),
            MovesNumberEvaluator(factor = 10),
            EaterEvaluator(weight = 20)
    )
    players.add(AlphabetaPlayer(depth = 7, color = Color.Black, evaluator = multi1))


    val multi2 = MultiEvaluator(
            PiecesCountEvaluator(regularWeight = 10, kingWeight = 20),
            TrappedKingsEvaluator(weight = -20),
            EaterEvaluator(weight = 40)
    )
//    players.add(AlphabetaPlayer(depth = 5, color = Color.Black, evaluator = multi2))

    game.play(*players.toTypedArray())
}
