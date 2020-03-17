package math.vector

import math.Num
import math.matrix.Matrix

interface Vector : Iterable<Num>, Cloneable {

    @Throws(IndexOutOfBoundsException::class)
    operator fun get(index: Int) : Num

    @Throws(IndexOutOfBoundsException::class)
    operator fun set(index: Int, value: Num)

    /**
     * Gets the number of elements in this vector
     */
    fun size() : Int

    fun copy(dest: Vector) {
        if (dest.size() < size())
            throw IllegalArgumentException("Dest vector (size=${dest.size()}) is smaller than this vector (size=${size()})")
        for (index in 0 until size())
            dest[index] = this[index]
    }

    public override fun clone(): Vector

    fun asMatrix(numRows: Int, numCols: Int): Matrix {
        if (numRows * numCols != size())
            throw IllegalArgumentException("numRols * numCols ($numRows * $numCols = ${numRows * numCols}) must be equal to size (${size()})")

        return VectorMatrix(this, numRows, numCols)
    }

    override operator fun iterator(): Iterator<Num> = VectorIterator(this)

    operator fun timesAssign(value: Num) {
        for (index in 0 until size())
            this[index] *= value
    }

    fun dotProduct(right: Vector): Num {

        if (size() != right.size())
            throw IllegalArgumentException("Vectors must have same size, " +
                    "but own size is ${size()} and other size is ${right.size()}")

        var result = 0f
        for ((index, own) in this.withIndex())
            result += own * right[index]

        return result
    }

    operator fun plusAssign(other: Vector) {
        if (size() != other.size())
            throw IllegalArgumentException("Vectors must have same size, " +
                    "but own size is ${size()} and other size is ${other.size()}")

        for ((index, value) in other.withIndex())
            this[index] += value
    }

    operator fun times(right: Vector) = dotProduct(right)

    operator fun times(right: Num) = ArrayVector(Array(size()){index -> this[index] * right})

    operator fun minusAssign(right: Vector) {
        plusAssign(-right)
    }

    operator fun minus(right: Vector): Vector {
        val result = clone()
        result -= right
        return result
    }

    operator fun unaryMinus(): Vector {
        val result = arrayZeroVector(size())
        for ((index, value) in withIndex())
            result[index] = -value
        return result
    }

    /**
     * Implementing classes can use this to easily override the toString method
     * by simply calling this method.
     */
   fun toVectorString(): String {
        var result = "Vector("
        for (index in 0 until size() - 1)
            result += "${this[index]},"
        result += "${this[size() - 1]})"
        return result
    }

    /**
     * Implementing classes can use this to easily override the equals method
     * by simply calling this method.
     */
    fun vectorEquals(other: Any?): Boolean {
        if (other is Vector) {
            if (other.size() != size())
                return false

            for (index in 0 until size())
                if (this[index] != other[index])
                    return false

            return true
        } else {
            return false
        }
    }
}

operator fun Num.times(vector: Vector) = vector * this

private class VectorIterator(private val vector: Vector) : Iterator<Num> {

    private var index = 0

    override fun hasNext() = index < vector.size()

    override fun next(): Num {
        if (!hasNext())
            throw NoSuchElementException()
        return vector[index++]
    }
}

internal class VectorMatrix(private val vector: Vector, private val rows: Int, private val cols: Int) : Matrix {

    override fun getNumRows() = rows

    override fun getNumCols() = cols

    override fun set(row: Int, col: Int, value: Num) {
        vector[col + row * cols] = value
    }

    override fun get(row: Int, col: Int) = vector[col + row * cols]

    override fun clone() = VectorMatrix(vector.clone(), rows, cols)

    override fun equals(other: Any?) = matrixEquals(other)

    override fun toString() = matrixToString()
}