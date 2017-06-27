package com.tsovedenski.ai.draughts.game.elements

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
enum class Color (val char: Char) {
    Black('●') {
        override fun opposite() = White
    },

    White('○') {
        override fun opposite() = Black
    };

    abstract fun opposite(): Color
}