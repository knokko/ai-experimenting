package math.vector

import math.Num

class TestArrayVector : TestVector() {

    override fun zeroVector(size: Int) = arrayZeroVector(size)

    override fun onesVector(size: Int) = arrayOnesVector(size)

    override fun vectorOf(vararg elements: Num) = arrayVectorOf(*elements)
}