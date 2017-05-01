package com.tsovedenski.ai.draughts.game

import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.elements.Point
import com.tsovedenski.ai.draughts.players.Player
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by Tsvetan Ovedenski on 01/05/2017.
 */
class StateTest {

    lateinit var state: State

    @Before
    fun setUp() {
        state = State(8, 12)
    }

    @Test
    fun `remove opponent pieces on diagonal`() {
        val moves = listOf(
                Move(5,0,4,1), // p1
                Move(2,3,3,2), // p2
                Move(4,1,2,3)  // p1
        )

        moves.forEach {
            state = state.apply(it)
        }

        assertNull(state[Point(3,2)]?.piece)
    }

    @Test
    fun `moves validity`() {
        state = state.apply(Move(2,3,3,4))
        state = state.apply(Move(3,4,4,5))

        val moves = hashMapOf(
                Move(1,1,2,1)   to false,
                Move(1,1,0,-1)  to false,
                Move(1,1,3,2)   to false,
                Move(3,4,2,3)   to false,
                Move(4,5,3,4)   to false,
                Move(4,5,5,6)   to false,
                Move(5,2,3,4)   to false,
                Move(6,1,4,3)   to false,
                Move(5,2,4,3)   to true,
                Move(5,6,3,4)   to true,
                Move(2,1,3,2)   to true
        )

        moves.forEach { move, isValid -> assertEquals(move.toString(), isValid, state.valid(move)) }
    }

    /**
       col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
       row+---+---+---+---+---+---+---+---+row
         0|   | ● |   | ● |   | ● |   | ● |0
        --+---+---+---+---+---+---+---+---+--
         1| ● |   | ● |   | ● |   | ● |   |1
        --+---+---+---+---+---+---+---+---+--
         2|   | ● |   | ░ |   | ● |   | ● |2
        --+---+---+---+---+---+---+---+---+--
         3| ░ |   | ░ |   | ░ |   | ░ |   |3
        --+---+---+---+---+---+---+---+---+--
         4|   | ░ |   | ░ |   | ● |   | ░ |4
        --+---+---+---+---+---+---+---+---+--
         5| ○ |   | ○ |   | ○ |   | ○ |   |5
        --+---+---+---+---+---+---+---+---+--
         6|   | ○ |   | ○ |   | ○ |   | ○ |6
        --+---+---+---+---+---+---+---+---+--
         7| ○ |   | ○ |   | ○ |   | ○ |   |7
        --+---+---+---+---+---+---+---+---+--
       col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
     */

}