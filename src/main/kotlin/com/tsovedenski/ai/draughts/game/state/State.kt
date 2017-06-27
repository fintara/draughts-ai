package com.tsovedenski.ai.draughts.game.state

import com.tsovedenski.ai.draughts.game.elements.Cell
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.elements.Point
import com.tsovedenski.ai.draughts.game.state.printers.FancyPrinter
import com.tsovedenski.ai.draughts.game.state.printers.Printer
import org.slf4j.LoggerFactory

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
data class State(val board: LinkedHashMap<Point, Cell>, val size: Int, val pieces: Int, val forcedCapture: Boolean = false) {

    constructor(size: Int, pieces: Int): this(generate(size, pieces), size, pieces)
    constructor(size: Int, pieces: Int, forcedCapture: Boolean): this(generate(size, pieces), size, pieces, forcedCapture)

    private val printer: Printer = FancyPrinter

    init {
        if (pieces % (size / 2) != 0) {
            throw IllegalArgumentException("State with size $size cannot have $pieces pieces")
        }
    }

    fun valid(move: Move, color: Color): Boolean {
        if (!valid(move)) {
            return false
        }

        // from has player's piece
        if (board[move.from]!!.piece!!.color != color) {
            log.trace("$move contains point (from) with not player's piece")
            return false
        }

        return true
    }

    fun valid(move: Move): Boolean {
        val from = move.from
        val to   = move.to

        log.trace("$move checking validity")

        // points exist
        if (!board.containsKey(from) || !board.containsKey(to)) {
            log.trace("$move contains point out of boundaries")
            return false
        }

        // move is diagonal
        if (Math.abs(from.row - to.row) != Math.abs(from.col - to.col)) {
            log.trace("$move is not diagonal")
            return false
        }

        // points are allowed
        if (!board[from]!!.allowed || !board[to]!!.allowed) {
            log.trace("$move contains point on not allowed square")
            return false
        }

        // from has piece and to doesn't
        if (board[from]!!.piece == null || board[to]!!.piece != null) {
            log.trace("$move contains point without piece (from) or existing piece (to)")
            return false
        }

        val diagonal = from.diagonal(to)
        if (diagonal.isNotEmpty() && diagonal.size and 1 == 0) {
            log.trace("$move goes through invalid diagonal (even number of cells in diagonal)")
            return false
        }
        diagonal.forEachIndexed { index, point ->
            val piece = board[point]!!.piece

            if (piece != null && piece.color == board[from]!!.piece!!.color) {
                log.trace("$move goes through invalid diagonal (own pieces)")
                return false
            }

            if (index and 1 == 0 && piece == null) {
                log.trace("$move goes through invalid diagonal (empty cell)")
                return false
            }

            if (index and 1 == 1 && piece != null) {
                log.trace("$move goes through invalid diagonal (non-empty cell)")
                return false
            }
        }

        val fromPiece = board[from]!!.piece!!
        if (!fromPiece.king) {
            // white can go only up (row--), black can go only down (row++)
            if ((fromPiece.color == Color.White && from.row - to.row < 0) ||
                 fromPiece.color == Color.Black && from.row - to.row > 0) {

                log.trace("$move goes backwards (from piece is not king)")
                return false
            }
        }

        return true
    }

    /**
     * Returns all possible destination points from a point
     */
    fun moves(point: Point): List<Point> {
        log.trace("Generating moves for point $point")
        val lists = (1..size/2).map { i ->
            listOf(
                    point + Point(i, i),
                    point + Point(i, -i),
                    point + Point(-i, -i),
                    point + Point(-i, i)
            ).filter { valid(Move(from = point, to = it)) }
        }

        val possiblePoints = lists.flatten()

        log.trace("Point $point has ${possiblePoints.size} possible move(s)")
        return possiblePoints
    }

    /**
     * Returns all possible moves for a color
     */
    fun moves(color: Color): List<Move> {
        log.trace("Finding possible moves for color $color")
        val moves = mutableListOf<Move>()
        var jumpCount = 0

        points(color).forEach { point ->
            val possibleMoves = moves(point)

            if (forcedCapture) {
                val jumpSizes = possibleMoves.map { point.diagonal(it).size }
                val maxJump = jumpSizes.maxBy { it }
                jumpCount += possibleMoves.map { point.diagonal(it).size }.filter { it > 0 && it == maxJump }.size
            }

            moves.addAll(possibleMoves.map { Move(from = point, to = it) })
        }

        if (forcedCapture && jumpCount == 1) {
            return listOf(
                    moves.sortedBy { -it.from.diagonal(it.to).size }.first()
            )
        }

        return moves.filter { valid(it, color) }
    }

    fun points(color: Color) = board.keys.filter { board[it]?.piece?.color == color }.toList()
    fun pieces(color: Color) = points(color).map { board[it]!! }

    fun count(color: Color) = board.values.map { it.piece?.color }.filter { it == color }.size

    fun apply(move: Move): State {
        if (!valid(move)) {
            log.error("Tried to apply illegal move $move")
            throw RuntimeException("Illegal move $move")
        }

        val cloned = copy(board = LinkedHashMap(board))

        // move the piece
        val piece = cloned.board[move.from]!!.piece!!
        cloned.board[move.from] = cloned.board[move.from]!!.copy(piece = null)
        cloned.board[move.to] = cloned.board[move.to]!!.copy(piece = piece)

        // remove opponents pieces on the path
        val diagonal = move.from.diagonal(move.to)
        diagonal.forEach { p ->
            log.trace("Going through intermediate point $p")
            if (cloned.board[p]!!.piece?.color == cloned.board[move.to]!!.piece!!.color.opposite()) {
                log.debug("Point $p has opponent's piece, removing it")
                cloned.board[p] = cloned.board[p]!!.copy(piece = null)
            }
        }

        // promote to king
        if ((move.to.row == 0        && piece.color == Color.White && !piece.king) ||
            (move.to.row == size - 1 && piece.color == Color.Black && !piece.king)) {

            log.debug("Promoting $piece at ${move.to} to king")
            cloned.board[move.to] = cloned.board[move.to]!!.copy(piece = piece.copy(king = true))
        }

        return cloned
    }

    fun winner(color: Color): Color? {
        if (isWinner(color)) {
            return color
        }

        return null
    }

    fun isWinner(color: Color) = count(color.opposite()) == 0
    fun isStalemate(color: Color) = moves(color).isEmpty()

    operator fun get(p: Point) = board[p]

    override fun toString(): String {
        return printer.print(this)
    }

    companion object {
        private val log = LoggerFactory.getLogger(State::class.java)

        private fun generate(size: Int, pieces: Int) = LinkedHashMap<Point, Cell>().apply {
            (0..size-1).forEach { row ->
                            (0..size-1).forEach { col ->
                                val point = Point(row, col)
                                val cell  = Cell.fromPosition(size, pieces, row, col)

                                put(point, cell)
                            }
                        }
        }
    }
}