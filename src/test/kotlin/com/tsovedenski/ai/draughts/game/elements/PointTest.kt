package com.tsovedenski.ai.draughts.game.elements

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by Tsvetan Ovedenski on 01/05/2017.
 */
class PointTest {

    @Test
    fun `proper diagonal when points next to each other forward 1`() {
        val p1 = Point(3,3)
        val p2 = Point(4,4)

        val diagonal = p1.diagonal(p2)
        assertEquals(0, diagonal.size)
    }

    @Test
    fun `proper diagonal when points next to each other forward 2`() {
        val p1 = Point(3,3)
        val p2 = Point(2,2)

        val diagonal = p1.diagonal(p2)
        assertEquals(0, diagonal.size)
    }

    @Test
    fun `proper diagonal when points next to each other backward 1`() {
        val p1 = Point(3,3)
        val p2 = Point(4,2)

        val diagonal = p1.diagonal(p2)
        assertEquals(0, diagonal.size)
    }

    @Test
    fun `proper diagonal when points next to each other backward 2`() {
        val p1 = Point(3,3)
        val p2 = Point(2,4)

        val diagonal = p1.diagonal(p2)
        assertEquals(0, diagonal.size)
    }

    @Test
    fun `proper diagonal when points at distance 1 forward`() {
        val p1 = Point(3,3)
        val p2 = Point(5,5)

        val diagonal = p1.diagonal(p2)
        assertEquals(1, diagonal.size)
        assertTrue(Point(4,4) in diagonal)
    }

    @Test
    fun `proper diagonal when points at distance 1 backward`() {
        val p1 = Point(3,3)
        val p2 = Point(5,1)

        val diagonal = p1.diagonal(p2)
        assertEquals(1, diagonal.size)
        assertTrue(Point(4,2) in diagonal)
    }

    @Test
    fun `proper diagonal when points at distance 2`() {
        val p1 = Point(3,3)
        val p2 = Point(6,6)

        val diagonal = p1.diagonal(p2)
        assertEquals(2, diagonal.size)
        assertTrue(Point(4,4) in diagonal)
        assertTrue(Point(5,5) in diagonal)
    }

    @Test
    fun `proper distance 1`() {
        val p1 = Point(3,3)
        val p2 = Point(3,3)

        assertEquals(0, p1 - p2)
        assertEquals(0, p2 - p1)
    }

    @Test
    fun `proper distance 2`() {
        val p1 = Point(3,3)
        val p2 = Point(6,6)

        assertEquals(2, p1 - p2)
        assertEquals(2, p2 - p1)
    }

    @Test
    fun `proper distance 3`() {
        val p1 = Point(2,1)
        val p2 = Point(0,3)

        assertEquals(1, p1 - p2)
        assertEquals(1, p2 - p1)
    }

}