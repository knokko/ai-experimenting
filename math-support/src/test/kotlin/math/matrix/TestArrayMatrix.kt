package math.matrix

import math.Num

class TestArrayMatrix : TestMatrix() {

    override fun zeroMatrix(numRows: Int, numCols: Int) = arrayZeroMatrix(numRows, numCols)

    override fun identityMatrix(size: Int) = arrayIdentityMatrix(size)

    override fun matrixOf(vararg elements: Array<Num>) = arrayMatrixOf(*elements)
}