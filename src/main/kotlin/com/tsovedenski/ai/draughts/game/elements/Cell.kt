package com.tsovedenski.ai.draughts.game.elements

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
data class Cell(val allowed: Boolean, val piece: Piece? = null) {
    init {
        if (!allowed && piece != null) {
            throw RuntimeException("Piece put on not allowed cell")
        }
    }
}