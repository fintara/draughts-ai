package com.tsovedenski.ai.draughts.players

import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.evaluators.Evaluator

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
abstract class ArtificialPlayer
constructor (color: Color, private val evaluator: Evaluator): Player(name = "Computer #${++id}", color = color) {

    protected fun evaluate(state: State, color: Color) = evaluator.evaluate(state, color)

    companion object {
        private var id = 0
    }
}