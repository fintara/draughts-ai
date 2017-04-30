package com.tsovedenski.ai.draughts.players

import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
abstract class Player (val name: String, val color: Color) {
    abstract fun move(state: State): Move

    override fun toString(): String {
        return "$name ($color ${color.char})"
    }
}