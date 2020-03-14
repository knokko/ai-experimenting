package math.vector

import math.Num
import math.matrix.arrayMatrixOf
import math.matrix.arrayZeroMatrix

class TestColVector: TestVector() {

    override fun zeroVector(size: Int) = arrayZeroMatrix(size, 1).getCol(0)

    override fun onesVector(size: Int) = arrayMatrixOf(*Array(size) {Array(1){1f}}).getCol(0)

    override fun vectorOf(vararg elements: Num) = arrayMatrixOf(*Array(elements.size){
        index -> Array(1) {elements[index]}
    }).getCol(0)
}