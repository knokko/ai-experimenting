package delta

import math.Num
import math.vector.Vector
import math.vector.arrayVectorOf
import java.util.*

fun main() {
    incSumTest()
}

private fun printResults(trainingData: Array<Pair<Vector,Num>>, testData: Array<Vector>, weights: Vector) {
    println("training data:")
    for (trainingPair in trainingData) {
        val trainingVector = trainingPair.first
        println("$trainingVector -> ${weights * trainingVector}")
    }
    println()
    println("test data:")
    for (testVector in testData)
        println("$testVector -> ${weights * testVector}")
    println()
    println("weights are $weights")
}

fun sumTest() {
    val rng = Random()

    val trainingData = Array(100) {
        val vector = arrayVectorOf(rng.nextFloat(), rng.nextFloat(), rng.nextFloat())
        val sum = vector[0] + vector[1] + vector[2]
        Pair(vector, sum)
    }

    val weights = deltaTraining(0.005f, *trainingData)

    val testData = arrayOf(
            arrayVectorOf(0.4f, 0.2f, 0.5f),
            arrayVectorOf(0.2f, 0.1f, 0.9f)
    )

    printResults(trainingData as Array<Pair<Vector, Num>>, testData as Array<Vector>, weights)
}

fun incSumTest() {
    val rng = Random()

    val trainingData = Array(100) {
        val vector = arrayVectorOf(rng.nextFloat(), rng.nextFloat(), rng.nextFloat())
        val sum = vector[0] + vector[1] + vector[2]
        Pair(vector, sum)
    }

    val weights = deltaTrainingIncremental(0.005f, *trainingData)

    val testData = arrayOf(
            arrayVectorOf(0.4f, 0.2f, 0.5f),
            arrayVectorOf(0.2f, 0.1f, 0.9f)
    )

    printResults(trainingData as Array<Pair<Vector, Num>>, testData as Array<Vector>, weights)
}

fun easyTest() {
    val trainingData = arrayOf(
            Pair(arrayVectorOf(0.1f, 0.2f, 0.3f, 0.4f), 0.4f),
            Pair(arrayVectorOf(0.3f, 0.1f, 0.2f, 0.2f), 0.2f),
            Pair(arrayVectorOf(0.5f, 0.4f, 0.3f, 0.1f), 0.8f),
            Pair(arrayVectorOf(0.1f, 0.1f, 0.1f, 0.1f), 0.2f),
            Pair(arrayVectorOf(0.6f, 0.5f, 0.7f, 0.1f), 1f)
    )

    val weights = deltaTraining(0.005f, *trainingData)

    val testData = arrayOf(
            arrayVectorOf(1f, 2f, 3f, 4f),
            arrayVectorOf(5f, 5f, 5f, 5f),
            arrayVectorOf(6f, 3f, 6f, 3f)
    )

    printResults(trainingData as Array<Pair<Vector, Num>>, testData as Array<Vector>, weights)
}