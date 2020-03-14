package hopfield

import math.matrix.Matrix
import math.matrix.arrayZeroMatrix
import math.vector.Vector
import java.util.*

fun train(vararg trainingPatterns: Vector): Matrix {
    val numVecs = trainingPatterns.size
    val vecSize = trainingPatterns[0].size()

    val weights = arrayZeroMatrix(vecSize, vecSize)
    for (rowIndex in 0 until vecSize) {
        for (colIndex in 0 until vecSize) {

            var currentValue = 0f
            for (pattern in trainingPatterns)
                currentValue += pattern[rowIndex] * pattern[colIndex]

            weights[rowIndex][colIndex] = currentValue / numVecs.toFloat()
        }
    }

    return weights
}

const val CONVERGE_COUNT = 500

fun associate(weights: Matrix, pattern: Vector) {

    var convergeCounter = CONVERGE_COUNT
    val rng = Random()

    var iterations = 0

    while (convergeCounter >= 0) {

        val index = rng.nextInt(pattern.size())
        val oldValue = pattern[index]

        var sum = 0f
        for (otherIndex in 0 until pattern.size()) {
            if (index != otherIndex) {
                sum += weights[index][otherIndex] * pattern[otherIndex]
            }
        }

        val newValue = if (sum < 0)
            -1f
        else
            1f

        if (oldValue != newValue) {
            convergeCounter = CONVERGE_COUNT
            pattern[index] = newValue
        }

        convergeCounter--
        iterations++
    }

    println("Took $iterations iterations")
}