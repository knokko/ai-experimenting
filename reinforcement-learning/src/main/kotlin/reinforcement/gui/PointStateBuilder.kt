package reinforcement.gui

internal class PointStateBuilder(val x: Int, val y: Int) {

    fun distance(destX: Int, destY: Int) = kotlin.math.sqrt(((destX - x) * (destX - x) + (destY - y) * (destY - y)).toDouble())
}