package math.vector

import math.Num

interface Vector : Iterable<Num> {

    @Throws(IndexOutOfBoundsException::class)
    operator fun get(index: Int) : Num

    @Throws(IndexOutOfBoundsException::class)
    operator fun set(index: Int, value: Num)

    /**
     * Gets the number of elements in this vector
     */
    fun size() : Int

    fun copy(dest: Vector) {
        if (dest.size() < size())
            throw IllegalArgumentException("Dest vector (size=${dest.size()}) is smaller than this vector (size=${size()})")
        for (index in 0 until size())
            dest[index] = this[index]
    }

    override operator fun iterator(): Iterator<Num> = VectorIterator(this)
}

private class VectorIterator(private val vector: Vector) : Iterator<Num> {

    private var index = 0

    override fun hasNext() = index < vector.size()

    override fun next(): Num {
        if (!hasNext())
            throw NoSuchElementException()
        return vector[index++]
    }
}