package com.tsovedenski.ai.draughts.players

import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
abstract class NamedPlayer (val name: String, override val color: Color): Player {
    override fun toString(): String {
        return "$name ($color ${color.char})"
    }
}