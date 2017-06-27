package com.tsovedenski.ai.draughts.game.elements

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
data class Move(val from: Point, val to: Point) {
    constructor(row1: Int, col1: Int, row2: Int, col2: Int): this(Point(row1, col1), Point(row2, col2))

    override fun toString(): String {
        return "Move[from=$from, to=$to]"
    }
}