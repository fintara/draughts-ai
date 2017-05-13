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

    val p1 = ConsolePlayer("John", Color.White)

    val multi = MultiEvaluator(
            PiecesCountEvaluator(regularWeight = 1, kingWeight = 2),
            TrappedKingsEvaluator(weight = 3)
    )

//    val p1 = AlphabetaPlayer(depth = 7, color = Color.White, evaluator = multi)
//    val p2 = AlphabetaPlayer(depth = 7, color = Color.Black, evaluator = KeepCloseEvaluator)
    val p2 = AlphabetaPlayer(depth = 7, color = Color.Black, evaluator = multi)

    game.play(p1, p2)
}
