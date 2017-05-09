package com.tsovedenski.ai.draughts.game.elements

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
data class Point (val row: Int, val col: Int) {

    fun diagonal(o: Point): List<Point> {
        val diff = when {
            row - o.row < 0 && col - o.col > 0 -> Point(1, -1)
            row - o.row < 0 && col - o.col < 0 -> Point(1, 1)
            row - o.row > 0 && col - o.col < 0 -> Point(-1, 1)
            else -> Point(-1, -1)
        }

        val list = mutableListOf<Point>()

        if (this - o < 1) {
            return list
        }

        (1..(this - o)).mapTo(list) { this + diff * it }

        return list
    }

    operator fun plus(other: Point) = Point(row + other.row, col + other.col)
    operator fun minus(other: Point) = Math.max(0, Math.abs(row - other.row) - 1)
    operator fun times(num: Int) = Point(row * num, col * num)

    override fun toString(): String {
        return "($row, $col)"
    }
}