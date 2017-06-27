package com.tsovedenski.ai.draughts

import com.tsovedenski.ai.draughts.game.Draughts
import com.tsovedenski.ai.draughts.game.Game
import com.tsovedenski.ai.draughts.game.elements.Color
import com.tsovedenski.ai.draughts.game.elements.Move
import com.tsovedenski.ai.draughts.game.evaluators.*
import com.tsovedenski.ai.draughts.game.state.State
import com.tsovedenski.ai.draughts.players.Player
import com.tsovedenski.ai.draughts.players.ai.AlphabetaPlayer
import java.util.*

/**
 * Created by Tsvetan Ovedenski on 14/05/17.
 */
val r = Random()

fun main(args: Array<String>) {

    val popsize = 16
    val population = mutableListOf<Gene>()

    val multi1 = MultiEvaluator(
            PiecesCountEvaluator(regularWeight = 30, kingWeight = 40),
            TrappedKingsEvaluator(weight = -10),
            MovesNumberEvaluator(factor = 10),
            AttackerEvaluator(weight = 20),
            PathToKingEvaluator(weight = 25)
    )

    val simplePlayer = AlphabetaPlayer(depth = 5, color = Color.White, evaluator = multi1)

    for (i in 1..popsize) {
        val gene = Gene(listOf(r.nextInt(30), r.nextInt(30), r.nextInt(30), r.nextInt(30), r.nextInt(30)))
        population.add(gene)
    }

    for (i in 0..100) {

        population.parallelStream().forEach { gene ->
            print(".")
            var moveCounter = 0
            var gameWinner: Player? = null

            val geneticListener = object: Game.ActionListener {
                override fun beforeStart() = Unit
                override fun beforeMove(state: State, player: Player) = Unit

                override fun afterMove(state: State, player: Player, move: Move) {
                    moveCounter++
                }

                override fun afterFinish(state: State, winner: Player?) {
                    gameWinner = winner
                }
            }

            val game = Draughts().apply { listener = geneticListener }

            val currentPlayer = gene.player

            game.play(simplePlayer, currentPlayer)

            gene.fitness = (game.maxMoves - moveCounter) * when (gameWinner) {
                currentPlayer -> 1
                else -> 0
            }
        }
        println()

        println("Fittest: ${population.maxBy { it.fitness }!!}")

        val newPopulation = mutableListOf<Gene>()

        for (j in 1..popsize) {
            val parent1 = (1..4).map { population[r.nextInt(popsize)] }.maxBy { it.fitness }!!
            val parent2 = (1..4).map { population[r.nextInt(popsize)] }.maxBy { it.fitness }!!
            val child = crossover(parent1, parent2)
            newPopulation.add(child)
        }

        population.clear()
        population.addAll(newPopulation)
    }

}

fun crossover(a: Gene, b: Gene): Gene {
    val genes = mutableListOf<Int>()

    for (i in 0..a.genes.size-1) {
        if (r.nextDouble() < 0.5) {
            genes.add(a.genes[i])
        } else {
            genes.add(b.genes[i])
        }
    }

    return Gene(genes)
}

data class Gene(val genes: List<Int>, var fitness: Int = 0) {
    val player: AlphabetaPlayer by lazy {
        val multi = MultiEvaluator(
                PiecesCountEvaluator(regularWeight = genes[0], kingWeight = 2*genes[0]),
                TrappedKingsEvaluator(weight = -genes[1]),
                MovesNumberEvaluator(factor = genes[2]),
                AttackerEvaluator(weight = genes[3]),
                PathToKingEvaluator(weight = genes[4])
        )

        AlphabetaPlayer(depth = 5, color = Color.Black, evaluator = multi)
    }
}
