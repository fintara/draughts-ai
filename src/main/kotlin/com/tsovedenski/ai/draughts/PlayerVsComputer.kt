package com.tsovedenski.ai.draughts

import com.tsovedenski.ai.draughts.game.Draughts
import com.tsovedenski.ai.draughts.game.Game
import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.evaluators.DifferenceEvaluator
import com.tsovedenski.ai.draughts.game.evaluators.KeepCloseEvaluator
import com.tsovedenski.ai.draughts.game.evaluators.PersonalEvaluator
import com.tsovedenski.ai.draughts.players.*

/**
 * Created by Tsvetan Ovedenski on 30/04/17.
 */
fun main(args: Array<String>) {

    val game = Draughts().apply {
        listener = ConsoleGameListener
    }

//    val p1 = ConsolePlayer("John", Color.White)

    val p1 = AlphabetaPlayer(depth = 5, color = Color.White, evaluator = KeepCloseEvaluator)
    val p2 = AlphabetaPlayer(depth = 7, color = Color.Black, evaluator = KeepCloseEvaluator)

    game.play(p1, p2)
}
