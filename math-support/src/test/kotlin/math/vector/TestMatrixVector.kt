package math.vector

import math.Num
import math.matrix.arrayMatrixOf
import math.matrix.arrayZeroMatrix

class TestMatrixVector: TestVector() {
    
    override fun zeroVector(size: Int) = arrayZeroMatrix(size, 1).asVector()

    override fun onesVector(size: Int) = arrayMatrixOf(Array(size){1f}).asVector()

    override fun vectorOf(vararg elements: Num) = arrayMatrixOf(elements.toTypedArray()).asVector()
}