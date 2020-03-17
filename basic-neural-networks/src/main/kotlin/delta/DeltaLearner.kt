package delta

import math.Num
import math.vector.Vector
import math.vector.arrayZeroVector
import math.vector.times

fun deltaTraining(learningRate: Num, vararg trainingSamples: Pair<Vector, Num>): Vector {

    val size = trainingSamples[0].first.size()
    for ((queryVector, _) in trainingSamples)
        if (queryVector.size() != size)
            throw IllegalArgumentException("Not all query vectors have the same size")
    val weights = arrayZeroVector(size)

    do {
        val weightDelta = arrayZeroVector(size)
        for ((queryVector, target) in trainingSamples) {

            val result = queryVector * weights
            weightDelta += learningRate * (target - result) * queryVector
        }

        weights += weightDelta
    } while (weightDelta * weightDelta > learningRate * 0.0000001f)

    return weights
}

fun deltaTrainingIncremental(learningRate: Num, vararg trainingSamples: Pair<Vector, Num>): Vector {

    val size = trainingSamples[0].first.size()
    for ((queryVector, _) in trainingSamples)
        if (queryVector.size() != size)
            throw IllegalArgumentException("Not all query vectors have the same size")
    val weights = arrayZeroVector(size)

    do {
        val oldWeights = weights.clone()
        for ((queryVector, target) in trainingSamples) {

            val result = queryVector * weights
            weights += learningRate * (target - result) * queryVector
        }

        val difference: Vector = weights - oldWeights
    } while (difference * difference > learningRate * 0.0000001f)

    return weights
}