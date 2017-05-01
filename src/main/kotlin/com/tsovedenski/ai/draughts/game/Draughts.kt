package com.tsovedenski.ai.draughts.game

import com.tsovedenski.ai.draughts.players.Player

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
class Draughts (val size: Int, val pieces: Int): Game {

    constructor(): this(8, 12)

    var listener: Game.ActionListener? = null

    init {
        if (pieces % (size / 2) != 0) {
            throw IllegalArgumentException("Board with size $size cannot have $pieces pieces")
        }
    }

    override fun play(vararg players: Player) {
        if (players.size != 2) {
            throw IllegalArgumentException("Expected 2 players (got ${players.size}")
        }

        listener?.beforeStart()

        var state = State(size, pieces)
        var winner: Player? = null

        let gameloop@ {
            while (true) {
                players.forEach { player ->
                    listener?.beforeMove(state, player)

                    val move = player.move(state)
                    state = state.apply(move)

                    listener?.afterMove(state, player, move)

                    state.winner(player.color)?.let { color ->
                        winner = players.find { it.color == color }
                    }

                    if (winner != null) {
                        return@gameloop
                    }
                }
            }
        }

        listener?.afterFinish(state, winner)
    }
}