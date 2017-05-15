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

//    val multi1 = MultiEvaluator(
//            PiecesCountEvaluator(regularWeight = 30, kingWeight = 40),
//            TrappedKingsEvaluator(weight = -10),
//            MovesNumberEvaluator(factor = 10),
//            AttackerEvaluator(weight = 20)
//    )
//    val multi1 = MultiEvaluator( // go1
//            PiecesCountEvaluator(regularWeight = 9, kingWeight = 18),
//            TrappedKingsEvaluator(weight = -7),
//            MovesNumberEvaluator(factor = 3),
//            AttackerEvaluator(weight = 10)
//    )
    val multi1 = MultiEvaluator( // go2
            PiecesCountEvaluator(regularWeight = 10, kingWeight = 15),
            TrappedKingsEvaluator(weight = -10),
            MovesNumberEvaluator(factor = 5),
            AttackerEvaluator(weight = 20),
            PathToKingEvaluator(weight = 25)
    )
    players.add(AlphabetaPlayer(depth = 8, color = Color.Black, evaluator = multi1))


//    val multi2 = MultiEvaluator(
//            PiecesCountEvaluator(regularWeight = 30, kingWeight = 40),
//            TrappedKingsEvaluator(weight = -10),
//            MovesNumberEvaluator(factor = 10),
//            AttackerEvaluator(weight = 20)
//    )
//    players.add(AlphabetaPlayer(depth = 8, color = Color.White, evaluator = multi2))

    game.play(*players.toTypedArray())
}
