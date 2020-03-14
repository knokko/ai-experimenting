package math.vector

import org.junit.Assert.assertEquals
import org.junit.Test

class TestArrayVector {

    @Test
    fun testZeros() {
        val vector = arrayZeroVector(2)
        assertEquals(2, vector.size())
        assertEquals(0f, vector[0])
        assertEquals(0f, vector[1])
    }

    @Test
    fun testOnes() {
        val vector = arrayOnesVector(2)
        assertEquals(2, vector.size())
        assertEquals(1f, vector[0])
        assertEquals(1f, vector[1])
    }

    @Test
    fun testVectorOf() {
        val vector = arrayVectorOf(1f,2f,3f,4f)
        assertEquals(4, vector.size())
        assertEquals(1f, vector[0])
        assertEquals(2f, vector[1])
        assertEquals(3f, vector[2])
        assertEquals(4f, vector[3])
    }

    @Test
    fun testSet() {
        val vector = arrayZeroVector(2)
        vector[0] = 4f
        vector[1] = -2f
        assertEquals(4f, vector[0])
        assertEquals(-2f, vector[1])
    }

    @Test
    fun testIterator() {
        val vector = arrayVectorOf(3f, 1f, 7f, 0f)
        for ((index, value) in vector.withIndex())
            assertEquals(vector[index], value)

        assertEquals(11f, vector.sum())
    }

    @Test
    fun testCopy() {
        val source = arrayVectorOf(3f, 1f)
        val dest = arrayZeroVector(3)
        source.copy(dest)
        assertEquals(3f, dest[0])
        assertEquals(1f, dest[1])
        assertEquals(0f, dest[2])
    }
}