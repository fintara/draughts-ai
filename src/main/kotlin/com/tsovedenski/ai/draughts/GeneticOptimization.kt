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
    val popsize = 32
    val population = mutableListOf<Pair<Gene, Player>>()
    val multi1 = MultiEvaluator(
            PiecesCountEvaluator(regularWeight = 30, kingWeight = 40),
            TrappedKingsEvaluator(weight = -10),
            MovesNumberEvaluator(factor = 10),
            EaterEvaluator(weight = 20)
    )
    val simplePlayer = AlphabetaPlayer(depth = 5, color = Color.White, evaluator = multi1)

    for (i in 1..popsize) {
        val gene = Gene(listOf(r.nextInt(30), r.nextInt(30), r.nextInt(30), r.nextInt(30), r.nextInt(30)))
        val multi = MultiEvaluator(
                PiecesCountEvaluator(regularWeight = gene.genes[0], kingWeight = gene.genes[1]),
                TrappedKingsEvaluator(weight = -gene.genes[2]),
                MovesNumberEvaluator(factor = gene.genes[3]),
                EaterEvaluator(weight = gene.genes[4])
        )
        val player = AlphabetaPlayer(depth = 5, color = Color.Black, evaluator = multi)
        population.add(Pair(gene, player))
    }

    for (i in 0..100) {

        population.parallelStream().forEach { (gene, player) ->
            print(".")
            var moveCounter = 0
            var gameWinner: Player? = null

            val geneticListener = object: Game.ActionListener {
                override fun beforeStart() {}

                override fun beforeMove(state: State, player: Player) {}

                override fun afterMove(state: State, player: Player, move: Move) {
                    moveCounter++
                }

                override fun afterFinish(state: State, winner: Player?) {
                    gameWinner = winner
                }
            }

            val game = Draughts().apply { listener = geneticListener }

            game.play(simplePlayer, player)

            gene.fitness = (game.maxMoves - moveCounter) * when (gameWinner == player) {
                true -> 1
                else -> 0
            }
        }
        println()

        println("Fittest: ${population.maxBy { it.first.fitness }!!.first}")

        val newPopulation = mutableListOf<Pair<Gene, Player>>()

        for (j in 1..popsize) {
            val parent1 = (1..4).map { population[r.nextInt(popsize)] }.maxBy { it.first.fitness }!!
            val parent2 = (1..4).map { population[r.nextInt(popsize)] }.maxBy { it.first.fitness }!!
            val child = crossover(parent1, parent2)
            newPopulation.add(child)
        }

        population.clear()
        population.addAll(newPopulation)
    }

}

fun crossover(a: Pair<Gene, Player>, b: Pair<Gene, Player>): Pair<Gene, Player> {
    val genes = mutableListOf<Int>()
    for (i in 0..a.first.genes.size-1) {
        if (r.nextDouble() < 0.5) {
            genes.add(a.first.genes[i])
        } else {
            genes.add(b.first.genes[i])
        }
    }
    val multi = MultiEvaluator(
            PiecesCountEvaluator(regularWeight = genes[0], kingWeight = genes[1]),
            TrappedKingsEvaluator(weight = -genes[2]),
            MovesNumberEvaluator(factor = genes[3]),
            EaterEvaluator(weight = genes[4])
    )
    val player = AlphabetaPlayer(depth = 5, color = Color.Black, evaluator = multi)
    return Pair(Gene(genes), player)
}

data class Gene(val genes: List<Int>, var fitness: Int = 0)
