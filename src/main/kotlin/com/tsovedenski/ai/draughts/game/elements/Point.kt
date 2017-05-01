package com.tsovedenski.ai.draughts.game.elements

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
data class Point (val row: Int, val col: Int) {

    fun diagonal(other: Point): List<Point> {
        val minRow = Math.min(row, other.row)
        val maxRow = Math.max(row, other.row)
        val minCol = Math.min(col, other.col)
        val maxCol = Math.max(col, other.col)

        val list = mutableListOf<Point>()

        for (row in minRow+1..maxRow-1) {
            for (col in minCol+1..maxCol-1) {
                list.add(Point(row, col))
            }
        }

        return list
    }

    operator fun plus(other: Point) = Point(row + other.row, col + other.col)

    operator fun minus(other: Point) = Math.abs(row - other.row) + Math.abs(col - other.col) - 1

    override fun toString(): String {
        return "($row, $col)"
    }
}