package math.vector

import math.Num
import math.matrix.arrayMatrixOf
import math.matrix.arrayZeroMatrix

class TestRowVector : TestVector() {

    override fun zeroVector(size: Int) = arrayZeroMatrix(1, size)[0]

    override fun onesVector(size: Int) = arrayMatrixOf(Array(size) {1f})[0]

    override fun vectorOf(vararg elements: Num) = arrayMatrixOf(elements.toTypedArray())[0]
}