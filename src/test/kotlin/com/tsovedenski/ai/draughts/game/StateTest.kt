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
class StateTest {

    val size = 8
    val pieces = 12

    lateinit var state: State

    @Before
    fun setUp() {
        state = State(size, pieces)
    }

    @Test
    fun `default state has stated number of pieces`() {
        assertEquals(pieces, state.count(Color.Black))
        assertEquals(pieces, state.count(Color.White))
    }

    @Test
    fun `opponent's number of pieces one less after jump`() {
        val moves = listOf(
                Move(5, 0, 4, 1), // p1
                Move(2, 3, 3, 2), // p2
                Move(4, 1, 2, 3)  // p1
        )

        moves.forEach {
            state = state.apply(it)
        }

        assertEquals(pieces - 1, state.count(Color.Black))
        assertEquals(pieces, state.count(Color.White))
    }

    @Test
    fun `single jump removes opponent's piece`() {
        val moves = listOf(
                Move(5, 0, 4, 1), // p1
                Move(2, 3, 3, 2), // p2
                Move(4, 1, 2, 3)  // p1
        )

        moves.forEach {
            state = state.apply(it)
        }

        assertNull(state[Point(3, 2)]?.piece)
    }

    @Test
    fun `double jump removes two opponent's pieces`() {
        val moves = listOf(
                Move(2, 3, 3, 4),
                Move(3, 4, 4, 5),
                Move(1, 4, 2, 3),
                Move(2, 3, 3, 2),
                Move(3, 2, 4, 1),
                Move(0, 5, 1, 4),
                Move(1, 4, 2, 3),
                Move(5, 0, 1, 4)
        )

        moves.forEach {
            state = state.apply(it)
        }

        assertNull(state[Point(4, 1)]?.piece)
        assertNull(state[Point(2, 3)]?.piece)
    }

    @Test
    fun `piece in the middle has two choices`() {
        val p = Point(5, 2)
        val moves = state.moves(p)
        assertEquals(2, moves.size)
        assertTrue(Point(4, 1) in moves)
        assertTrue(Point(4, 3) in moves)
    }

    @Test
    fun `piece can jump over two opponents pieces with one empty between`() {
        state = state.apply(Move(2, 5, 3, 4))
        state = state.apply(Move(3, 4, 4, 3))
        state = state.apply(Move(1, 6, 2, 5))

        val p = Point(5, 2)
        val moves = state.moves(p)
        assertEquals(3, moves.size)
        assertTrue(Point(4, 1) in moves)
        assertTrue(Point(3, 4) in moves)
        assertTrue(Point(1, 6) in moves)
    }

    @Test
    fun `piece cannot jump over two opponents pieces without empty between`() {
        val moves = listOf(
                Move(5,0,4,1),
                Move(2,1,3,2),
                Move(5,2,4,3),
                Move(3,2,5,0),
                Move(6,1,5,2)
        )

        moves.forEach {
            state = state.apply(it)
        }

        val badMove = Move(2,5,6,1)

        assertFalse(state.valid(badMove))
    }

    @Test
    fun `piece cannot jump over three opponents pieces without empty between`() {
        val moves = listOf(
                Move(5,2,4,3),
                Move(2,1,3,2),
                Move(6,1,5,2),
                Move(1,0,2,1),
                Move(5,2,4,1),
                Move(0,1,1,0),
                Move(5,6,4,5),
                Move(2,1,3,0),
                Move(6,3,5,2),
                Move(1,0,2,1),
                Move(6,7,5,6),
                Move(2,5,3,6),
                Move(5,6,4,7),
                Move(1,4,2,5),
                Move(7,4,6,3)
        )

        moves.forEach {
            state = state.apply(it)
        }

        val badMove = Move(3,0,7,4)

        assertFalse(state.valid(badMove))
    }

    @Test
    fun `piece cannot jump over two opponents without empty at all`() {
        val moves = listOf(
                Move(5,0,4,1),
                Move(2,1,3,2),
                Move(5,2,4,3),
                Move(3,2,5,0),
                Move(6,1,5,2),
                Move(1,0,2,1),
                Move(5,4,4,5),
                Move(0,1,1,0),
                Move(5,6,4,7),
                Move(2,1,3,2),
                Move(4,3,2,1),
                Move(1,0,3,2),
                Move(5,2,4,1),
                Move(1,2,2,1),
                Move(4,1,3,0),
                Move(2,5,3,6),
                Move(4,7,2,5),
                Move(1,4,3,6),
                Move(6,5,5,4),
                Move(0,5,1,4),
                Move(6,7,5,6),
                Move(1,4,2,5),
                Move(7,6,6,5),
                Move(3,2,4,1),
                Move(7,0,6,1),
                Move(2,1,3,2),
                Move(3,0,2,1),
                Move(4,1,5,2),
                Move(6,3,4,1),
                Move(0,3,1,2),
                Move(2,1,1,0),
                Move(1,2,2,1),
                Move(4,1,3,0)
        )

        moves.forEach {
            state = state.apply(it)
        }

        val badMove = Move(3,6,6,3)

        assertFalse(state.valid(badMove))
    }

    @Test
    fun `moves validity`() {
        state = state.apply(Move(2, 3, 3, 4))
        state = state.apply(Move(3, 4, 4, 5))

        val moves = hashMapOf(
                Move(0, 0, 1, 1)  to false,
                Move(1, 1, 2, 1)  to false,
                Move(1, 1, 0, -1) to false,
                Move(1, 1, 3, 2)  to false,
                Move(3, 4, 2, 3)  to false,
                Move(4, 5, 3, 4)  to false,
                Move(4, 5, 5, 6)  to false,
                Move(5, 2, 3, 4)  to false,
                Move(6, 1, 4, 3)  to false,
                Move(5, 2, 4, 3)  to true,
                Move(5, 6, 3, 4)  to true,
                Move(2, 1, 3, 2)  to true
        )

        moves.forEach { move, isValid -> assertEquals(move.toString(), isValid, state.valid(move)) }
    }

    @Test
    fun `moves by own player`() {
        state = state.apply(Move(2, 3, 3, 4))
        state = state.apply(Move(3, 4, 4, 5))

        val moves = listOf(
                Triple(Move(5, 2, 4, 3), Color.Black, false),
                Triple(Move(5, 2, 4, 3), Color.White, true),
                Triple(Move(2, 5, 3, 4), Color.Black, true),
                Triple(Move(2, 5, 3, 4), Color.White, false)
        )

        moves.forEach { (move, color, isValid) -> assertEquals(move.toString(), isValid, state.valid(move, color)) }
    }

    /**
     * col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
     * row+---+---+---+---+---+---+---+---+row
     *   0|   | ● |   | ● |   | ● |   | ● |0
     *  --+---+---+---+---+---+---+---+---+--
     *   1| ● |   | ● |   | ● |   | ● |   |1
     *  --+---+---+---+---+---+---+---+---+--
     *   2|   | ● |   | ░ |   | ● |   | ● |2
     *   --+---+---+---+---+---+---+---+---+--
     *   3| ░ |   | ░ |   | ░ |   | ░ |   |3
     *   --+---+---+---+---+---+---+---+---+--
     *   4|   | ░ |   | ░ |   | ● |   | ░ |4
     *   --+---+---+---+---+---+---+---+---+--
     *   5| ○ |   | ○ |   | ○ |   | ○ |   |5
     *   --+---+---+---+---+---+---+---+---+--
     *   6|   | ○ |   | ○ |   | ○ |   | ○ |6
     *   --+---+---+---+---+---+---+---+---+--
     *   7| ○ |   | ○ |   | ○ |   | ○ |   |7
     *  --+---+---+---+---+---+---+---+---+--
     * col| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |col
     */

    @Test
    fun `piece promoted to king`() {
        val moves = listOf(
                Move(2, 3, 3, 4),
                Move(3, 4, 4, 5),
                Move(1, 4, 2, 3),
                Move(2, 3, 3, 2),
                Move(3, 2, 4, 1),
                Move(0, 5, 1, 4),
                Move(1, 4, 2, 3),
                Move(5, 0, 1, 4),
                Move(1, 4, 0, 5)
        )



        moves.forEach {
            state = state.apply(it)
        }

        assertNotNull(state[Point(0, 5)]?.piece)
        assertTrue(state[Point(0, 5)]!!.piece!!.king)
        assertEquals(Color.White, state[Point(0, 5)]!!.piece!!.color)
    }
}