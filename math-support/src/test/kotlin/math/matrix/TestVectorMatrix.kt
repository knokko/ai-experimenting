package math.matrix

import math.Num
import math.vector.arrayVectorOf
import math.vector.arrayZeroVector

class TestVectorMatrix : TestMatrix() {

    override fun zeroMatrix(numRows: Int, numCols: Int) = arrayZeroVector(numRows * numCols).asMatrix(numRows, numCols)

    override fun identityMatrix(size: Int) = arrayVectorOf(*Array(size * size){
        index -> if(index % size == index / size) 1f else 0f
    }.toFloatArray()).asMatrix(size, size)

    override fun matrixOf(vararg elements: Array<Num>) = arrayVectorOf(*Array(elements.size * elements[0].size){
        index -> elements[index / elements[0].size][index % elements[0].size]
    }.toFloatArray()).asMatrix(elements.size, elements[0].size)
}