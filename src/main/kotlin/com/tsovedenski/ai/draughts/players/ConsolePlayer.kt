package com.tsovedenski.ai.draughts.players

import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.elements.Point
import com.tsovedenski.ai.draughts.game.State
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
class ConsolePlayer (name: String): Player(name) {

    private val scanner = Scanner(System.`in`)

    override fun move(state: State): Move {
        var move: Move?

        do {
            print("$this, Your move (row,col-row,col): ")
            move = parse(scanner.nextLine().trim())
        } while (move == null || !state.valid(move, this))

        return move
    }

    private fun parse(text: String): Move? {
        val matcher = MOVE_PATTERN.matcher(text)
        if (!matcher.find()) {
            return null
        }

        try {
            val from = Point(matcher.group(1).toInt(), matcher.group(2).toInt())
            val to = Point(matcher.group(3).toInt(), matcher.group(4).toInt())
            return Move(from, to)
        } catch (t: Throwable) {
            t.printStackTrace()
            return null
        }
    }

    companion object {
        private val MOVE_PATTERN = Pattern.compile("^(\\d+),(\\d+)-(\\d+),(\\d+)$")

    }
}