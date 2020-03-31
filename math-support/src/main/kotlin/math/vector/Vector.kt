package math.vector

import math.Num
import math.matrix.Matrix

/**
 * Represents a mathematical vector of numbers.
 */
interface Vector : Iterable<Num>, Cloneable {

    // <-- Abstract operations -->
    @Throws(IndexOutOfBoundsException::class)
    operator fun get(index: Int) : Num

    @Throws(IndexOutOfBoundsException::class)
    operator fun set(index: Int, value: Num)

    /**
     * Gets the number of elements in this vector
     */
    fun size() : Int

    /**
     * Creates a new vector with the same length as this vector.
     *
     * It is recommended to create a new vector with the same class as this vector,
     * but that is not required.
     *
     * @sample math.vector.TestVector.testZeroCopy
     */
    fun zeroCopy(): Vector

    // <-- Cloning operations -->

    /**
     * Copies the elements of this vector into [dest].
     *
     * If [dest] has more elements than this vector, only the first this.size()
     * elements of [dest] will be affected.
     *
     * @param dest The destination vector where the elements of this vector will be copied to
     * @throws IllegalArgumentException If [dest] has less elements than this vector
     * @sample math.vector.TestVector.testCopy
     */
    @Throws(IllegalArgumentException::class)
    fun copy(dest: Vector) {
        if (dest.size() < size())
            throw IllegalArgumentException("Dest vector (size=${dest.size()}) is smaller than this vector (size=${size()})")

        for ((index, value) in this.withIndex())
            dest[index] = value
    }

    /**
     * Creates a copy of this vector.
     *
     * It will have the same size and the same elements, but it will not share its elements with this vector.
     * So modifying the clone will *not* modify this vector.
     *
     * @return A copy of this vector
     * @sample math.vector.TestVector.testClone
     */
    public override fun clone(): Vector {
        val result = zeroCopy()
        copy(result)
        return result
    }

    // <-- Other operations -->

    /**
     * Creates a matrix view of this vector.
     *
     * The resulting matrix will have [numRows] rows and [numCols] columns.
     * The element at result(row,col) will refer to this(col + row * [numCols]).
     * Modifying this vector *will* affect the matrix view and vice versa.
     *
     * @throws IllegalArgumentException If [numCols] * [numRows] is not equal to this.size()
     * @return A matrix view over this vector
     * @sample math.vector.TestVector.testAsMatrix
     */
    @Throws(IllegalArgumentException::class)
    fun asMatrix(numRows: Int, numCols: Int): Matrix {
        if (numRows * numCols != size())
            throw IllegalArgumentException("numRows * numCols ($numRows * $numCols = ${numRows * numCols}) must be equal to size (${size()})")

        return VectorMatrix(this, numRows, numCols)
    }

    // <-- Mathematical operations with scalars -->

    /**
     * Multiplies (all elements in) this vector with [scalar].
     *
     * Note that you shouldn't call this method explicitly because
     * you can also use the times assign operator.
     */
    fun scalarMultiplication(scalar: Num) {
        for (index in 0 until size())
            this[index] *= scalar
    }

    /**
     * Divides all elements in this vector by [scalar].
     *
     * Note that you shouldn't call this method explicitly because
     * you can also use the division assign operator.
     */
    fun scalarDivision(scalar: Num) = scalarMultiplication(1f / scalar)

    /**
     * Multiplies (all elements of) this vector with [scalar] and returns the resulting vector.
     * This vector will not be affected.
     *
     * Note that you shouldn't call this method explicitly because
     * you can also use the times operator.
     */
    fun scalarProduct(scalar: Num): Vector {
        val result = clone()
        result *= scalar
        return result
    }

    /**
     * Divides (all elements of) this vector by [scalar] and returns the resulting vector.
     * This vector will not be affected.
     *
     * Note that you shouldn't call this method explicitly because
     * you can also use the division operator.
     */
    fun scalarQuotient(scalar: Num): Vector {
        val result = clone()
        result /= scalar
        return result
    }

    // <-- Mathematical operations with vectors -->

    /**
     * Adds the other vector to this vector (element wise).
     * This method only changes this vector, not the other vector.
     *
     * Note that you shouldn't call this method explicitly, but use the plus assign operator instead.
     *
     * @throws IllegalArgumentException If the size of this vector is not the same as the size of [other]
     */
    @Throws(IllegalArgumentException::class)
    fun vectorAddition(other: Vector) {
        if (size() != other.size())
            throw IllegalArgumentException("The sizes of this vector (${size()}) and " +
                    "the other vector (${other.size()}) must be the same")
        for ((index, value) in other.withIndex())
            this[index] += value
    }

    /**
     * Adds the other vector to this vector (element wise) and returns the resulting vector.
     * This method doesn't modify this vector or the other vector.
     *
     * Note that you shouldn't call this method explicitly, but use the plus operator instead.
     *
     * @throws IllegalArgumentException If the size of this vector is not the same as the size of [other]
     */
    @Throws(IllegalArgumentException::class)
    fun vectorSum(other: Vector): Vector {
        val result = clone()
        result += other
        return result
    }

    /**
     * Subtracts the other vector from this vector (element wise).
     * This method only changes this vector, not the other vector.
     *
     * Note that you shouldn't call this method explicitly, but use the minus assign operator instead.
     *
     * @throws IllegalArgumentException If the size of this vector is not the same as the size of [right]
     */
    @Throws(IllegalArgumentException::class)
    fun vectorSubtraction(right: Vector) = vectorAddition(-right)

    /**
     * Subtracts the other vector from this vector (element wise) and returns the resulting vector.
     * This method doesn't modify this vector or the other vector.
     *
     * Note that you shouldn't call this method explicitly, but use the minus operator instead.
     *
     * @throws IllegalArgumentException If the size of this vector is not the same as the size of [right]
     */
    @Throws(IllegalArgumentException::class)
    fun vectorDifference(right: Vector) = vectorSum(-right)

    /**
     * Computes and returns the dot product of this vector with the other vector.
     *
     * Note that you shouldn't call this method explicitly, but use the times operator instead.
     *
     * @throws IllegalArgumentException If the size of this vector is not the same as the size of [other]
     * @return The dot product of this vector with [other]
     */
    @Throws(IllegalArgumentException::class)
    fun dotProduct(other: Vector): Num {

        if (size() != other.size())
            throw IllegalArgumentException("Vectors must have same size, " +
                    "but own size is ${size()} and other size is ${other.size()}")

        var result = 0f
        for ((index, own) in this.withIndex())
            result += own * other[index]

        return result
    }

    // <-- Operator overloading methods -->

    override operator fun iterator(): Iterator<Num> = VectorIterator(this)

    operator fun plus(other: Vector) = vectorSum(other)

    operator fun plusAssign(other: Vector) = vectorAddition(other)

    operator fun minus(right: Vector) = vectorDifference(right)

    operator fun minusAssign(right: Vector) = vectorSubtraction(right)

    operator fun times(scalar: Num) = scalarProduct(scalar)

    operator fun times(right: Vector) = dotProduct(right)

    operator fun timesAssign(scalar: Num) = scalarMultiplication(scalar)

    operator fun div(scalar: Num) = scalarQuotient(scalar)

    operator fun divAssign(scalar: Num) = scalarDivision(scalar)

    operator fun unaryMinus() = this * -1f

    // <-- Helper methods for overriding toString(), equals() and hashCode() -->

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
     * Implementing classes can use this to easily override the hashCode method
     * by simply returning the result of this method.
     */
    fun vectorHashcode() : Int {
        var result = 7 * size()
        for (element in this)
            result = 3 * result + (11456f * element).toInt()
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

// <-- Operator overloading for when the vector is on the right side of the operator -->

operator fun Num.times(vector: Vector) = vector.scalarProduct(this)

// <-- Internal support classes -->
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