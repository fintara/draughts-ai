package com.tsovedenski.ai.draughts

import com.tsovedenski.ai.draughts.game.Draughts
import com.tsovedenski.ai.draughts.game.Game
import com.tsovedenski.ai.draughts.game.State
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.players.*

/**
 * Created by Tsvetan Ovedenski on 30/04/17.
 */
val moves = listOf(
        Move(5,0,4,1),
        Move(2,1,3,2),
        Move(4,1,3,0),
        Move(1,0,2,1),
        Move(5,6,4,5),
        Move(2,3,3,4),
        Move(4,5,2,3),
        Move(1,2,3,4),
        Move(3,0,1,2)
)

fun main(args: Array<String>) {

    val game = Draughts().apply {
        listener = object: Game.ActionListener {
            override fun beforeStart() {}
            override fun beforeMove(state: State, player: Player) {}
            override fun afterMove(state: State, player: Player, move: Move) {}
            override fun afterFinish(state: State, winner: Player?) {
                println(state)
            }
        }
    }

    val p1 = Replayer(Color.White, moves, 0)
    val p2 = Replayer(Color.Black, moves, 1)

    game.play(p1, p2)
}
