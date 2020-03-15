package hopfield

import math.matrix.arrayZeroMatrix
import math.matrix.imageToMatrix
import math.matrix.matrixToImage
import math.vector.Vector
import util.loadImage
import util.saveImage
import java.awt.Color.*
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val pattern1 = patternFromImage("pattern1")
    val pattern2 = patternFromImage("pattern2")
    val pattern3 = patternFromImage("pattern3")

    val weights = hopfieldTrain(pattern1, pattern2, pattern3)

    val test = patternFromImage("test1.10")

    hopfieldAssociate(weights, test)
    patternToImage(test, 10, "test1")
}

fun patternToImage(pattern: Vector, width: Int, name: String) {

    val height = pattern.size() / width
    val image = matrixToImage(pattern.asMatrix(height, width)){value ->
        when (value) {
            -1f -> BLACK
            1f -> WHITE
            else -> throw IllegalArgumentException("Not all elements are -1 or 1")
        }
    }

    saveImage(image, name)
}

fun patternFromImage(name: String): Vector {
    val image = loadImage(name)

    val matrix = arrayZeroMatrix(image.height, image.width)
    imageToMatrix(image, matrix){color ->
        when (color) {
            BLACK -> -1f
            WHITE -> 1f
            else -> throw IllegalArgumentException("Not all pixels are black or white")
        }
    }

    return matrix.asVector()
}