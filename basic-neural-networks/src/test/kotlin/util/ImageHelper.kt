package util

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun loadImage(path: String): BufferedImage {
    val someClass = DummyClass().javaClass
    val resource = someClass.classLoader.getResource("$path.png")
    return ImageIO.read(resource)
}

fun saveImage(image: BufferedImage, path: String) {
    ImageIO.write(image, "PNG", File("$path.png"))
}

class DummyClass {}