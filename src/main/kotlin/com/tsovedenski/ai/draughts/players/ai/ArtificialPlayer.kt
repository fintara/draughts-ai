package com.tsovedenski.ai.draughts.players.ai

import com.tsovedenski.ai.draughts.game.state.State
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.evaluators.Evaluator
import com.tsovedenski.ai.draughts.players.NamedPlayer

/**
 * Created by Tsvetan Ovedenski on 03/05/17.
 */
abstract class ArtificialPlayer
constructor (color: Color, private val evaluator: Evaluator): NamedPlayer(name = "Computer #${++id}", color = color) {

    protected fun evaluate(state: State, color: Color) = evaluator.evaluate(state, color)

    companion object {
        private var id = 0
    }
}