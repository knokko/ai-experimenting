package hopfield

import math.vector.Vector
import math.vector.arrayZeroVector
import java.awt.Color
import java.awt.Color.*
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val pattern1 = patternFromImage("pattern1")
    val pattern2 = patternFromImage("pattern2")
    val pattern3 = patternFromImage("pattern3")

    val weights = train(pattern1, pattern2, pattern3)

    val test = patternFromImage("test1.20")

    associate(weights, test)
    patternToImage(test, 10, "test1")
}

fun patternToImage(pattern: Vector, width: Int, name: String) {
    val height = pattern.size() / width
    val image = BufferedImage(width, height, TYPE_INT_RGB)

    val white = WHITE.rgb
    val black = BLACK.rgb

    for (x in 0 until width) {
        for (y in 0 until height) {
            val vecValue = pattern[x + y * width]
            if (vecValue == -1f)
                image.setRGB(x, y, black)
            else if (vecValue == 1f)
                image.setRGB(x, y, white)
            else
                throw IllegalArgumentException("Not all elements are -1 or 1")
        }
    }

    ImageIO.write(image, "PNG", File(name + ".png"))
}

fun patternFromImage(name: String): Vector {
    val someClass = DummyClass().javaClass
    val resource = someClass.classLoader.getResource("hopfield/$name.png")
    val image = ImageIO.read(resource)

    val vector = arrayZeroVector(image.width * image.height)
    for (x in 0 until image.width) {
        for (y in 0 until image.height) {
            val pixel = Color(image.getRGB(x, y))

            val vecValue = if (pixel.red == 255)
                1f
            else if (pixel.red == 0)
                -1f
            else
                throw IllegalArgumentException("Not all pixels are black or white")

            vector[x + y * image.width] = vecValue
        }
    }

    return vector
}

class DummyClass {

}