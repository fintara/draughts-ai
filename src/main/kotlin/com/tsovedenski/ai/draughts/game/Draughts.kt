package com.tsovedenski.ai.draughts.game

import com.tsovedenski.ai.draughts.players.Player

/**
 * Created by Tsvetan Ovedenski on 30/04/2017.
 */
class Draughts (val size: Int, val pieces: Int): Game {

    constructor(): this(8, 12)

    lateinit var listener: Game.ActionListener

    init {
        if (pieces % (size / 2) != 0) {
            throw IllegalArgumentException("Board with size $size cannot have $pieces pieces")
        }
    }

    override fun play(vararg players: Player) {
        if (players.size != 2) {
            throw IllegalArgumentException("Expected 2 players (got ${players.size}")
        }

        listener.beforeStart()

        var state = State(size, pieces)
        var winner: Player? = null

//        println(state.toString())

        let gameloop@ {
            while (true) {
                players.forEach { player ->
                    state = turn(state, player)
                    listener.afterMove(state, player)

                    winner = state.winner(player)

                    if (winner != null) {
                        return@gameloop
                    }
                }
            }
        }

        listener.afterFinish(winner)
    }

    private fun turn(state: State, player: Player): State {
        listener.beforeMove(state, player)
        val move = player.move(state)
        return state.apply(move)
    }
}