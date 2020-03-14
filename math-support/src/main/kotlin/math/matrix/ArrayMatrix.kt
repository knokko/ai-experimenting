package math.matrix

import math.Num
import math.vector.ArrayVector

class ArrayMatrix(private val array: Array<Num>, private val rows: Int, private val cols: Int) : Matrix {

    override fun getNumRows() = rows

    override fun getNumCols() = cols

    @Throws(IndexOutOfBoundsException::class)
    override fun set(row: Int, col: Int, value: Num) {
        array[indexFor(row, col)] = value
    }

    @Throws(IndexOutOfBoundsException::class)
    override fun get(row: Int, col: Int) = array[indexFor(row, col)]

    @Throws(IndexOutOfBoundsException::class)
    private fun indexFor(row: Int, col: Int): Int {
        if (row < 0 || col < 0 || row >= rows || col >= cols)
            throw IndexOutOfBoundsException("Matrix size is ($rows, $cols), but tried to access ($row, $col)")
        return col + row * cols
    }
}

fun arrayZeroMatrix(numRows: Int, numCols: Int): ArrayMatrix = ArrayMatrix(Array(numRows * numCols){0.0f},
        numRows, numCols)

fun arrayIdentityMatrix(size: Int): ArrayMatrix {
    val matrix = arrayZeroMatrix(size, size)
    for (index in 0 until size)
        matrix[index][index] = 1.0f
    return matrix
}

fun arrayMatrixOf(vararg array: Array<Num>): ArrayMatrix {
    if (array.isEmpty())
        return arrayZeroMatrix(0,0)

    val numCols = array[0].size
    for (row in array)
        if (row.size != numCols)
            throw IllegalArgumentException("All rows must have the same size")

    val matrix = arrayZeroMatrix(array.size, numCols)
    for ((rowIndex, row) in array.withIndex())
        matrix[rowIndex] = ArrayVector(row)

    return matrix
}