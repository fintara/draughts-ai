package com.tsovedenski.ai.draughts.game.elements

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
enum class Color (val char: Char) {
    Black('●') {
        override fun opposite(): com.tsovedenski.ai.draughts.game.elements.Color {
            return com.tsovedenski.ai.draughts.game.elements.Color.White
        }
    },

    White('○') {
        override fun opposite(): com.tsovedenski.ai.draughts.game.elements.Color {
            return com.tsovedenski.ai.draughts.game.elements.Color.Black
        }
    };

    abstract fun opposite(): com.tsovedenski.ai.draughts.game.elements.Color
}