package com.tsovedenski.ai.draughts.game.elements

import com.tsovedenski.ai.draughts.players.Player

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
data class Move (val player: Player, val from: Point, val to: Point) {
    override fun toString(): String {
        return "Move[from=$from, to=$to]"
    }
}