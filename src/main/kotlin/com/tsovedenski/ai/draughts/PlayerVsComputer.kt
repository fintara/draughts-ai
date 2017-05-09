package com.tsovedenski.ai.draughts

import com.tsovedenski.ai.draughts.game.Draughts
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.evaluators.*
import com.tsovedenski.ai.draughts.players.*

/**
 * Created by Tsvetan Ovedenski on 30/04/17.
 */
fun main(args: Array<String>) {

    val game = Draughts().apply {
        listener = ConsoleGameListener
    }

//    val p1 = ConsolePlayer("John", Color.White)

    val multi = MultiEvaluator(
            PiecesNumberEvaluator,
            MovesNumberEvaluator,
            BlockedMovesEvaluator
    )

    val p1 = AlphabetaPlayer(depth = 7, color = Color.White, evaluator = multi)
//    val p2 = AlphabetaPlayer(depth = 7, color = Color.Black, evaluator = KeepCloseEvaluator)
    val p2 = AlphabetaPlayer(depth = 3, color = Color.Black, evaluator = multi)

    game.play(p1, p2)
}