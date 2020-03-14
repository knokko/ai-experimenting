package math.vector

import math.Num

class ArrayVector(private val array: Array<Num>) : Vector {

    override fun get(index: Int) = array[index]

    override fun set(index: Int, value: Num) {
        array[index] = value
    }

    override fun size() = array.size
}

fun arrayZeroVector(size: Int) = ArrayVector(Array(size) {0f})

fun arrayOnesVector(size: Int) = ArrayVector(Array(size) {1f})

fun arrayVectorOf(vararg elements: Num) = ArrayVector(Array(elements.size) {index -> elements[index]})