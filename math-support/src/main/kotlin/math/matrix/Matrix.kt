package math.matrix

import math.Num
import math.vector.Vector
import math.vector.arrayZeroVector
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_ARGB

interface Matrix : Iterable<Vector>, Cloneable {

    /**
     * The number of rows that this matrix has
     */
    fun getNumRows() : Int

    /**
     * The number of columns that this matrix has
     */
    fun getNumCols() : Int

    /**
     * Set the number at (row, col) to value
     */
    @Throws(IndexOutOfBoundsException::class)
    fun set(row: Int, col: Int, value: Num)

    /**
     * Gets the number at (row, col)
     */
    @Throws(IndexOutOfBoundsException::class)
    fun get(row: Int, col: Int) : Num

    /**
     * Copies newValues into the rowIndex'th row of this matrix
     */
    @Throws(IndexOutOfBoundsException::class)
    fun setRow(rowIndex: Int, newValues: Vector) {
        newValues.copy(getRow(rowIndex))
    }

    /**
     * Gets a row vector for the rowIndex'th row of this matrix.
     * Changes made to the row vector will be visible in this matrix and vice versa.
     */
    @Throws(IndexOutOfBoundsException::class)
    fun getRow(rowIndex: Int) : Vector = RowVector(this, rowIndex)

    /**
     * Copies the rowIndex'th row of this matrix into dest.
     */
    @Throws(IndexOutOfBoundsException::class)
    fun getRow(rowIndex: Int, dest: Vector) {
        getRow(rowIndex).copy(dest)
    }

    /**
     * Copies newValues in the colIndex'th column of this matrix
     */
    @Throws(IndexOutOfBoundsException::class)
    fun setCol(colIndex: Int, newValues: Vector) {
        newValues.copy(getCol(colIndex))
    }

    /**
     * Gets a column vector for the colIndex'th column of this matrix.
     * Changes made to the column vector will be visible in this matrix and vice versa.
     */
    @Throws(IndexOutOfBoundsException::class)
    fun getCol(colIndex: Int) : Vector = ColVector(this, colIndex)

    /**
     * Copies the colIndex'th column of this matrix into dest
     */
    @Throws(IndexOutOfBoundsException::class)
    fun getCol(colIndex: Int, dest: Vector) {
        getCol(colIndex).copy(dest)
    }

    /**
     * Copies newRow into the rowIndex'th row of this matrix
     */
    @Throws(IndexOutOfBoundsException::class)
    operator fun set(rowIndex: Int, newRow: Vector) {
        setRow(rowIndex, newRow)
    }

    /**
     * Gets a row vector for the rowIndex'th row of this matrix.
     * Changes made to the row vector will be visible in this matrix and vice versa.
     */
    @Throws(IndexOutOfBoundsException::class)
    operator fun get(rowIndex: Int) = getRow(rowIndex)

    fun rows(): Iterator<Vector> = RowIterator(this)

    fun cols(): Iterator<Vector> = ColIterator(this)

    override fun iterator(): Iterator<Vector> = rows()

    operator fun divAssign(value: Num) {
        for (rowIndex in 0 until getNumRows())
            for (colIndex in 0 until getNumCols())
                this[rowIndex][colIndex] /= value
    }

    /**
     * Implementations can use this method to easily override toString by simply calling this method
     */
    fun matrixToString(): String {
        var result = "Matrix[\n"
        for (row in rows())
            result += row.toVectorString() + "\n"
        return "$result]"
    }

    /**
     * Implementations can use this method to easily override equals by simply calling this method
     */
    fun matrixEquals(other: Any?): Boolean {
        if (other is Matrix) {
            if (other.getNumRows() != this.getNumRows())
                return false

            for (rowIndex in 0 until getNumRows())
                if (this[rowIndex] != other[rowIndex])
                    return false

            return true
        } else {
            return false
        }
    }

    fun asVector(): Vector = MatrixVector(this)

    operator fun times(right: Matrix): Matrix {
        if (getNumCols() != right.getNumRows())
            throw IllegalArgumentException("Number of columns of this matrix (${getNumCols()}) must be equal " +
                    "to the number of rows of the right matrix ({${right.getNumRows()})")

        val result = arrayZeroMatrix(getNumRows(), right.getNumCols())
        for (rowIndex in 0 until result.getNumRows()) {
            for (colIndex in 0 until result.getNumCols()) {

                var value = 0f
                for (sourceIndex in 0 until getNumCols())
                    value += this[rowIndex][sourceIndex] * right[sourceIndex][colIndex]

                result[rowIndex][colIndex] = value
            }
        }

        return result
    }

    operator fun times(right: Vector): Vector {
        if (getNumCols() != right.size())
            throw IllegalArgumentException("The number of columns of this matrix (${getNumCols()}) must be " +
                    "equal to the size of the vector (${right.size()})")

        val result = arrayZeroVector(getNumRows())
        for ((rowIndex, row) in rows().withIndex()) {

            var value = 0f
            for (colIndex in 0 until getNumCols())
                value += row[colIndex] * right[colIndex]

            result[rowIndex] = value
        }

        return result
    }

    public override fun clone(): Matrix
}

internal class RowVector(val owner: Matrix, val rowIndex: Int) : Vector {

    override fun get(index: Int) = owner.get(rowIndex, index)

    override fun set(index: Int, value: Num) {
        owner.set(rowIndex, index, value)
    }

    override fun size() = owner.getNumCols()

    override fun clone(): Vector {
        val clone = arrayZeroVector(owner.getNumCols())
        copy(clone)
        return clone
    }

    override fun equals(other: Any?) = vectorEquals(other)

    override fun toString() = toVectorString()
}

internal class ColVector(val owner: Matrix, val colIndex: Int) : Vector {

    override fun get(index: Int) = owner.get(index, colIndex)

    override fun set(index: Int, value: Num) {
        owner.set(index, colIndex, value)
    }

    override fun size() = owner.getNumRows()

    override fun clone(): Vector {
        val clone = arrayZeroVector(owner.getNumRows())
        copy(clone)
        return clone
    }

    override fun equals(other: Any?) = vectorEquals(other)

    override fun toString() = toVectorString()
}

private class RowIterator(private val matrix: Matrix) : Iterator<Vector> {

    private var index = 0

    override fun hasNext() = index < matrix.getNumRows()

    override fun next(): Vector {
        if (!hasNext())
            throw NoSuchElementException()
        return matrix[index++]
    }
}

private class ColIterator(private val matrix: Matrix) : Iterator<Vector> {

    private var index = 0

    override fun hasNext() = index < matrix.getNumCols()

    override fun next(): Vector {
        if (!hasNext())
            throw NoSuchElementException()
        return matrix.getCol(index++)
    }
}

internal class MatrixVector(private val matrix: Matrix) : Vector {

    override fun get(index: Int) = matrix[index / matrix.getNumCols()][index % matrix.getNumCols()]

    override fun set(index: Int, value: Num) {
        matrix[index / matrix.getNumCols()][index % matrix.getNumCols()] = value
    }

    override fun size() = matrix.getNumRows() * matrix.getNumCols()

    override fun clone() = MatrixVector(matrix.clone())

    override fun equals(other: Any?) = vectorEquals(other)

    override fun toString() = toVectorString()
}

fun imageToMatrix(image: BufferedImage, dest: Matrix, mapping: (Color) -> Num) {
    if (image.width != dest.getNumCols())
        throw IllegalArgumentException("The width (${image.width}) should be the same as the number of columns (${dest.getNumCols()})")
    if (image.height != dest.getNumRows())
        throw IllegalArgumentException("The height (${image.height}) should be the same as the number of rows (${dest.getNumRows()})")

    for ((rowIndex, row) in dest.rows().withIndex()) {
        for (colIndex in 0 until row.size()) {
            val rgb = image.getRGB(colIndex, rowIndex)
            val pixel = Color(rgb, true)
            row[colIndex] = mapping(pixel)
        }
    }
}

fun matrixToImage(matrix: Matrix, mapping: (Num) -> Color): BufferedImage {
    val image = BufferedImage(matrix.getNumCols(), matrix.getNumCols(), TYPE_INT_ARGB)
    for ((rowIndex, row) in matrix.rows().withIndex()) {
        for ((colIndex, value) in row.withIndex()) {
            val pixel = mapping(value)
            image.setRGB(colIndex, rowIndex, pixel.rgb)
        }
    }

    return image
}