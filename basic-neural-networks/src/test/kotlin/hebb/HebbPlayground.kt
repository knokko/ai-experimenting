package hebb

import math.Num
import math.matrix.arrayZeroMatrix
import math.matrix.imageToMatrix
import math.matrix.matrixToImage
import math.vector.Vector
import util.loadImage
import util.saveImage
import java.awt.Color

fun main() {

    val pattern1 = patternFromImage("pattern1")
    val pattern2 = patternFromImage("pattern2")

    val testPattern = patternFromImage("test1.1")

    val weights = hebbTrain(pattern2, pattern1)
    patternToImage(weights[0], 10, "weight0")
    patternToImage(weights[4], 10, "weight4")
    patternToImage(weights[10], 10, "weight10")
    patternToImage(weights[14], 10, "weight14")

    for (counter in 1..5) {
        hebbAssociate(weights, testPattern, 1, 0.1f){x -> x}
        patternToImage(testPattern, 10, "hebbResult$counter")
    }

}

fun patternToImage(pattern: Vector, width: Int, name: String) {

    val height = pattern.size() / width
    val image = matrixToImage(pattern.asMatrix(height, width)){value ->
        val component = cap(100f + 90f * value, 0, 255)
        Color(component, component, component)
    }

    saveImage(image, name)
}

fun cap(value: Num, min: Int, max: Int) = value.toInt().coerceAtMost(max).coerceAtLeast(min)

fun patternFromImage(name: String): Vector {
    val image = loadImage(name)

    val matrix = arrayZeroMatrix(image.height, image.width)
    imageToMatrix(image, matrix){color ->
        when (color) {
            Color.BLACK -> 1f
            Color.WHITE -> 0f
            else -> throw IllegalArgumentException("Not all pixels are black or white")
        }
    }

    return matrix.asVector()
}