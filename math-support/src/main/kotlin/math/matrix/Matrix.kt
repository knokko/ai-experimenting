package math.matrix

import math.Num
import math.vector.Vector

interface Matrix : Iterable<Vector> {

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
}

private class RowVector(val owner: Matrix, val rowIndex: Int) : Vector {

    override fun get(index: Int) = owner.get(rowIndex, index)

    override fun set(index: Int, value: Num) {
        owner.set(rowIndex, index, value)
    }

    override fun size() = owner.getNumCols()
}

private class ColVector(val owner: Matrix, val colIndex: Int) : Vector {

    override fun get(index: Int) = owner.get(index, colIndex)

    override fun set(index: Int, value: Num) {
        owner.set(index, colIndex, value)
    }

    override fun size() = owner.getNumRows()
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