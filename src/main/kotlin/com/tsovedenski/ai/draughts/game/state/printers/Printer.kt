package com.tsovedenski.ai.draughts.game.state.printers

import com.tsovedenski.ai.draughts.game.state.State

/**
 * Created by Tsvetan Ovedenski on 13/05/17.
 */
interface Printer {
    fun print(state: State): String
}