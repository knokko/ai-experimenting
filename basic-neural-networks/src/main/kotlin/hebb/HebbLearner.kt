package hebb

import math.Num
import math.matrix.Matrix
import math.matrix.arrayZeroMatrix
import math.vector.Vector
import math.vector.arrayZeroVector
import kotlin.math.abs
import kotlin.math.exp

fun hebbTrain(vararg patterns: Vector): Matrix {

    val weights = arrayZeroMatrix(patterns[0].size(), patterns[0].size())
    for (pattern in patterns)
        for ((firstIndex, firstValue) in pattern.withIndex())
            for ((secondIndex, secondValue) in pattern.withIndex())
                weights[firstIndex][secondIndex] += firstValue * secondValue

    weights /= patterns.size.toFloat()
    return weights
}

fun hebbAssociate(weights: Matrix, pattern: Vector, numSteps: Int,
                  learningRate: Num, activationFunction: (Num) -> Num = {x -> 1f / (1f + exp(0f - x))}) {

    val tempPattern = arrayZeroVector(pattern.size())
    val size = pattern.size()

    for (counter in 0 until numSteps) {

        for (own in 0 until size) {
            for (other in 0 until size) {
                if (own != other) {
                    tempPattern[own] += weights[own][other] * pattern[other]

                    val product = pattern[own] * pattern[other]
                    weights[own][other] += learningRate * product
                    println("product is $product")
                    if (abs(product) < 0.1f)
                        weights[own][other] *= 0.2f
                }
            }

            pattern[own] = activationFunction(tempPattern[own])
        }

        tempPattern *= 0f
    }
}