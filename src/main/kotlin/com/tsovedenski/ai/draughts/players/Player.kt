package com.tsovedenski.ai.draughts.players

import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.state.State
import com.tsovedenski.ai.draughts.game.elements.Color

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
interface Player {
    val color: Color
    fun move(state: State): Move?
}