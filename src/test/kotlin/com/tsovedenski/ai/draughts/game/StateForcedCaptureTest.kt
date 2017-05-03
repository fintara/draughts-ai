package com.tsovedenski.ai.draughts.game

import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.elements.Point
import com.tsovedenski.ai.draughts.players.Player
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by Tsvetan Ovedenski on 01/05/2017.
 */
class StateForcedCaptureTest {

    val size = 8
    val pieces = 12

    lateinit var state: State

    @Before
    fun setUp() {
        state = State(size, pieces, true)
    }

    @Test

            /**
             * col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
             * row+---+---+---+---+---+---+---+---+row
             *   0|   | ● |   | ● |   | ● |   | ● |0
             *  --+---+---+---+---+---+---+---+---+--
             *   1| ● |   | ● |   | ● |   | ░ |   |1
             *  --+---+---+---+---+---+---+---+---+--
             *   2|   | ● |   | ● |   | ░ |   | ● |2
             *   --+---+---+---+---+---+---+---+---+--
             *   3| ░ |   | ░ |   | ● |   | ░ |   |3
             *   --+---+---+---+---+---+---+---+---+--
             *   4|   | ○ |   | ░ |   | ░ |   | ░ |4
             *   --+---+---+---+---+---+---+---+---+--
             *   5| ○ |   | ● |   | ○ |   | ○ |   |5
             *   --+---+---+---+---+---+---+---+---+--
             *   6|   | ○ |   | ○ |   | ○ |   | ○ |6
             *   --+---+---+---+---+---+---+---+---+--
             *   7| ○ |   | ○ |   | ○ |   | ○ |   |7
             *  --+---+---+---+---+---+---+---+---+--
             * col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
             */

    fun `piece forced to jump longest possible`() {
        val moves = listOf(
                Move(5,2,4,1),
                Move(2,5,3,4),
                Move(3,4,4,3),
                Move(4,3,5,2),
                Move(1,6,2,5),
                Move(2,5,3,4)
        )
        moves.forEach { state = state.apply(it) }

        val colorPossible = state.moves(Color.White)
        assertEquals(1, colorPossible.size)
        assertTrue(Move(6,1,2,5) in colorPossible)
    }

    @Test
    fun `piece forced to jump over one opponent 1`() {
        val moves = listOf(
                Move(5,2,4,1),
                Move(2,5,3,4),
                Move(3,4,4,3),
                Move(4,3,5,2)
        )
        moves.forEach { state = state.apply(it) }

        val colorPossible = state.moves(Color.White)
        assertEquals(1, colorPossible.size)
        assertTrue(Move(6,1,4,3) in colorPossible)
    }

    @Test

            /**
             * col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
             * row+---+---+---+---+---+---+---+---+row
             *   0|   | ● |   | ● |   | ● |   | ● |0
             *  --+---+---+---+---+---+---+---+---+--
             *   1| ● |   | ● |   | ● |   | ● |   |1
             *  --+---+---+---+---+---+---+---+---+--
             *   2|   | ░ |   | ● |   | ░ |   | ● |2
             *   --+---+---+---+---+---+---+---+---+--
             *   3| ░ |   | ░ |   | ○ |   | ● |   |3
             *   --+---+---+---+---+---+---+---+---+--
             *   4|   | ░ |   | ░ |   | ░ |   | ░ |4
             *   --+---+---+---+---+---+---+---+---+--
             *   5| ● |   | ○ |   | ░ |   | ○ |   |5
             *   --+---+---+---+---+---+---+---+---+--
             *   6|   | ○ |   | ○ |   | ○ |   | ○ |6
             *   --+---+---+---+---+---+---+---+---+--
             *   7| ○ |   | ○ |   | ○ |   | ○ |   |7
             *  --+---+---+---+---+---+---+---+---+--
             * col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
             */

    fun `piece forced to jump over one opponent 2`() {
        val moves = listOf(
                Move(5,0,4,1),
                Move(2,1,3,2),
                Move(5,4,4,5),
                Move(3,2,5,0),
                Move(4,5,3,4),
                Move(2,5,3,6)
        )
        moves.forEach { state = state.apply(it) }

        val colorPossible = state.moves(Color.Black)
        assertEquals(1, colorPossible.size)
        assertTrue(Move(2,3,4,5) in colorPossible)
    }





    @Test

            /**
             * col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
             * row+---+---+---+---+---+---+---+---+row
             *   0|   | ● |   | ● |   | ● |   | ● |0
             *  --+---+---+---+---+---+---+---+---+--
             *   1| ● |   | ● |   | ● |   | ● |   |1
             *  --+---+---+---+---+---+---+---+---+--
             *   2|   | ░ |   | ● |   | ● |   | ● |2
             *   --+---+---+---+---+---+---+---+---+--
             *   3| ░ |   | ░ |   | ○ |   | ░ |   |3
             *   --+---+---+---+---+---+---+---+---+--
             *   4|   | ░ |   | ░ |   | ░ |   | ░ |4
             *   --+---+---+---+---+---+---+---+---+--
             *   5| ● |   | ○ |   | ░ |   | ○ |   |5
             *   --+---+---+---+---+---+---+---+---+--
             *   6|   | ○ |   | ○ |   | ○ |   | ○ |6
             *   --+---+---+---+---+---+---+---+---+--
             *   7| ○ |   | ○ |   | ○ |   | ○ |   |7
             *  --+---+---+---+---+---+---+---+---+--
             * col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
             */

    fun `piece not forced to jump over one opponent when two possibilities 1`() {
        val moves = listOf(
                Move(5,0,4,1),
                Move(2,1,3,2),
                Move(5,4,4,5),
                Move(3,2,5,0),
                Move(4,5,3,4)
        )
        moves.forEach { state = state.apply(it) }

        val possible = state.moves(Color.Black)
        assertEquals(7, possible.size)
    }

    @Test
    fun `piece not forced to jump over one opponent when two possibilities 2`() {
        val moves = listOf(
                Move(2,1,3,0),
                Move(3,0,4,1),
                Move(2,3,3,2),
                Move(3,2,4,3)
        )
        moves.forEach { state = state.apply(it) }

        val colorPossible = state.moves(Color.White)
        assertEquals(7, colorPossible.size)
    }

    @Test

            /**
             * col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
             * row+---+---+---+---+---+---+---+---+row
             *   0|   | ● |   | ● |   | ● |   | ● |0
             *  --+---+---+---+---+---+---+---+---+--
             *   1| ● |   | ● |   | ● |   | ░ |   |1
             *  --+---+---+---+---+---+---+---+---+--
             *   2|   | ● |   | ● |   | ● |   | ● |2
             *   --+---+---+---+---+---+---+---+---+--
             *   3| ░ |   | ░ |   | ░ |   | ░ |   |3
             *   --+---+---+---+---+---+---+---+---+--
             *   4|   | ░ |   | ● |   | ░ |   | ░ |4
             *   --+---+---+---+---+---+---+---+---+--
             *   5| ○ |   | ○ |   | ○ |   | ○ |   |5
             *   --+---+---+---+---+---+---+---+---+--
             *   6|   | ○ |   | ○ |   | ○ |   | ○ |6
             *   --+---+---+---+---+---+---+---+---+--
             *   7| ○ |   | ○ |   | ○ |   | ○ |   |7
             *  --+---+---+---+---+---+---+---+---+--
             * col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
             */

    fun `piece not forced to jump over two opponents pieces when two possibilities`() {
        val moves = listOf(
                Move(2, 5, 3, 4),
                Move(3, 4, 4, 3),
                Move(1, 6, 2, 5)
        )

        moves.forEach {
            state = state.apply(it)
        }

        val colorPossible = state.moves(Color.White)
        assertEquals(8, colorPossible.size)
    }

    @Test

            /**
             * col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
             * row+---+---+---+---+---+---+---+---+row
             *   0|   | ● |   | ● |   | ● |   | ● |0
             *  --+---+---+---+---+---+---+---+---+--
             *   1| ● |   | ● |   | ● |   | ░ |   |1
             *  --+---+---+---+---+---+---+---+---+--
             *   2|   | ● |   | ● |   | ● |   | ● |2
             *   --+---+---+---+---+---+---+---+---+--
             *   3| ░ |   | ░ |   | ░ |   | ░ |   |3
             *   --+---+---+---+---+---+---+---+---+--
             *   4|   | ░ |   | ● |   | ○ |   | ░ |4
             *   --+---+---+---+---+---+---+---+---+--
             *   5| ○ |   | ○ |   | ░ |   | ○ |   |5
             *   --+---+---+---+---+---+---+---+---+--
             *   6|   | ○ |   | ○ |   | ○ |   | ○ |6
             *   --+---+---+---+---+---+---+---+---+--
             *   7| ○ |   | ○ |   | ○ |   | ○ |   |7
             *  --+---+---+---+---+---+---+---+---+--
             * col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
             */

    fun `piece forced to jump over two opponents pieces when one possibility`() {
        val moves = listOf(
                Move(2, 5, 3, 4),
                Move(3, 4, 4, 3),
                Move(1, 6, 2, 5),
                Move(5, 4, 4, 5)
        )

        moves.forEach {
            state = state.apply(it)
        }

        val colorPossible = state.moves(Color.White)
        assertEquals(1, colorPossible.size)
    }
}