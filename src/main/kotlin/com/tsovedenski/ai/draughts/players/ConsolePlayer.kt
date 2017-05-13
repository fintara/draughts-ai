package com.tsovedenski.ai.draughts.players

import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.elements.Point
import com.tsovedenski.ai.draughts.game.state.State
import com.tsovedenski.ai.draughts.game.elements.Color
import org.slf4j.LoggerFactory
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
class ConsolePlayer (name: String, color: Color): NamedPlayer(name, color) {

    private val scanner = Scanner(System.`in`)

    override fun move(state: State): Move? {
        var move: Move?

        val moves = state.moves(color)
        println("Possible moves:")
        moves.forEachIndexed { index, move -> println("${index+1} -> ${move.from} to ${move.to} <- ${index+1}") }

        do {
            print("$this, Your move (row,col,row,col): ")
            val text = scanner.nextLine().trim()

            if (text.toLowerCase() == "q") {
                return null
            }

            move = parse(text, moves)
        } while (move == null || !state.valid(move, color) || move !in state.moves(color))

        return move
    }

    private fun parse(text: String, moves: List<Move>): Move? {
        var matcher = NUM_PATTERN.matcher(text)

        if (matcher.find()) {
            try {
                val idx = matcher.group(1).toInt() - 1

                if (idx >= 0 && idx < moves.size) {
                    return moves[idx]
                }
            } catch (t: Throwable) {}
        }


        matcher = MOVE_PATTERN.matcher(text)
        if (!matcher.find()) {
            log.info("$text did not contain valid move")
            return null
        }

        try {
            val from = Point(matcher.group(1).toInt(), matcher.group(2).toInt())
            val to = Point(matcher.group(3).toInt(), matcher.group(4).toInt())
            return Move(from, to)
        } catch (t: Throwable) {
            log.error("Got error during parsing $text")
            t.printStackTrace()
            return null
        }
    }

    companion object {
        private val NUM_PATTERN = Pattern.compile("^(\\d+)$")
        private val MOVE_PATTERN = Pattern.compile("^(\\d+),(\\d+),(\\d+),(\\d+)$")
        private val log = LoggerFactory.getLogger(ConsolePlayer::class.java)
    }
}