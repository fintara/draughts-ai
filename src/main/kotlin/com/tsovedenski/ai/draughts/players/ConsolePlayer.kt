package com.tsovedenski.ai.draughts.players

import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.elements.Point
import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color
import org.slf4j.LoggerFactory
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
class ConsolePlayer (name: String, color: Color): Player(name, color) {

    private val scanner = Scanner(System.`in`)

    override fun move(state: State): Move {
        var move: Move?

        do {
            print("$this, Your move (row,col,row,col): ")
            move = parse(scanner.nextLine().trim())
        } while (move == null || !state.valid(move))

        return move
    }

    private fun parse(text: String): Move? {
        if (text.toLowerCase() == "q") {
            println("$this gives up! Winner is the opponent. Exiting...")
            System.exit(0)
        }

        val matcher = MOVE_PATTERN.matcher(text)
        if (!matcher.find()) {
            log.info("$text did not contain valid move")
            return null
        }

        try {
            val from = Point(matcher.group(1).toInt(), matcher.group(2).toInt())
            val to = Point(matcher.group(3).toInt(), matcher.group(4).toInt())
            return Move(this, from, to)
        } catch (t: Throwable) {
            log.error("Got error during parsing $text")
            t.printStackTrace()
            return null
        }
    }

    companion object {
        private val MOVE_PATTERN = Pattern.compile("^(\\d+),(\\d+),(\\d+),(\\d+)$")
        private val log = LoggerFactory.getLogger(ConsolePlayer::class.java)
    }
}