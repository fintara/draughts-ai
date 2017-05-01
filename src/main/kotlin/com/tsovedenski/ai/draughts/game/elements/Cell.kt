package com.tsovedenski.ai.draughts.game.elements

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
data class Cell(val allowed: Boolean, val piece: Piece? = null) {
    init {
        if (!allowed && piece != null) {
            throw RuntimeException("Piece put on not allowed cell")
        }
    }
    companion object {
        fun fromPosition(boardSize: Int, boardPieces: Int, row: Int, col: Int): Cell {
            val allowed = (row * boardSize + col + 1) and 1 == row and 1

            val piece = when {
                allowed && row < boardPieces / (boardSize / 2) -> Piece(color = Color.Black)
                allowed && row >= boardSize - boardPieces / (boardSize / 2) -> Piece(color = Color.White)
                else -> null
            }

            return Cell(piece = piece, allowed = allowed)
        }
    }
}