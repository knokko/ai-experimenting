package math.vector

import math.Num
import org.junit.Assert.*
import org.junit.Test

abstract class TestVector {

    abstract fun zeroVector(size: Int): Vector

    abstract fun onesVector(size: Int): Vector

    abstract fun vectorOf(vararg elements: Num): Vector

    // <-- Testing construction methods -->

    @Test
    fun testZeros() {
        val vector = zeroVector(2)
        assertEquals(2, vector.size())
        assertEquals(0f, vector[0])
        assertEquals(0f, vector[1])
    }

    @Test
    fun testOnes() {
        val vector = onesVector(2)
        assertEquals(2, vector.size())
        assertEquals(1f, vector[0])
        assertEquals(1f, vector[1])
    }

    @Test
    fun testVectorOf() {
        val vector = vectorOf(1f,2f,3f,4f)
        assertEquals(4, vector.size())
        assertEquals(1f, vector[0])
        assertEquals(2f, vector[1])
        assertEquals(3f, vector[2])
        assertEquals(4f, vector[3])
    }

    // <-- Test get/set -->
    @Test
    fun testSet() {
        val vector = zeroVector(2)
        vector[0] = 4f
        vector[1] = -2f
        assertEquals(4f, vector[0])
        assertEquals(-2f, vector[1])
    }

    // <-- Test iteration -->
    @Test
    fun testIterator() {
        val vector = vectorOf(3f, 1f, 7f, 0f)
        for ((index, value) in vector.withIndex())
            assertEquals(vector[index], value)

        assertEquals(11f, vector.sum())
    }

    // <-- Test copy operations -->

    @Test
    fun testZeroCopy() {
        val source = vectorOf(4f, -2f)
        val clone = source.zeroCopy()
        assertEquals(source, vectorOf(4f, -2f))
        assertEquals(clone, vectorOf(0f, 0f))
    }

    @Test
    fun testCopy() {
        val source = vectorOf(3f, 1f)
        val dest = zeroVector(3)
        source.copy(dest)
        assertEquals(3f, dest[0])
        assertEquals(1f, dest[1])
        assertEquals(0f, dest[2])
    }

    @Test
    fun testClone() {
        val vector = vectorOf(-3f, 4f)
        val cloned = vector.clone()
        assertTrue(vector == cloned)
        assertFalse(vector === cloned)

        vector[0] = 1f
        assertEquals(-3f, cloned[0])
    }

    // <-- Test other operations -->

    @Test
    fun testAsMatrix() {
        val vector = vectorOf(2f, 1f, 4f, 3f, 0f, 8f)
        val matrix = vector.asMatrix(2, 3)
        assertEquals(2f, matrix[0][0])
        assertEquals(1f, matrix[0][1])
        assertEquals(4f, matrix[0][2])
        assertEquals(3f, matrix[1][0])
        assertEquals(0f, matrix[1][1])
        assertEquals(8f, matrix[1][2])

        matrix[0][0] = 1f
        assertEquals(1f, vector[0])
    }

    // <-- Test methods that should be overridden -->

    @Test
    fun testEquals() {
        val first = vectorOf(1f, 3f)
        val second = vectorOf(1f, 3f)
        val third = vectorOf(2f, 3f)
        assertTrue(first == second)
        assertFalse(second == third)
    }

    @Test
    fun testHashcode() {
        val first = vectorOf(1f, 2f, 5f)
        val second = vectorOf(1f, 2f, 5f)

        assertTrue(first.hashCode() == second.hashCode())
    }

    // <-- Test math operations -->

    @Test
    fun testScalarOperations() {
        assertEquals(vectorOf(4f, 3f, 3f).scalarProduct(1.5f), vectorOf(6f, 4.5f, 4.5f))
        assertEquals(vectorOf(6f, 12f).scalarQuotient(3f), vectorOf(2f, 4f))

        val vector = vectorOf(1f, 4f, 2f)
        vector.scalarMultiplication(6f)
        assertEquals(vector, vectorOf(6f, 24f, 12f))
        vector.scalarDivision(3f)
        assertEquals(vector, vectorOf(2f, 8f, 4f))
    }

    @Test
    fun testVectorOperations() {
        val vector1 = vectorOf(5f, 3f, 7f)
        val vector2 = vectorOf(2f, 2f, 3f)

        assertEquals(vector1.dotProduct(vector2), 37f)
        assertEquals(vector1.vectorSum(vector2), vectorOf(7f, 5f, 10f))
        assertEquals(vector1.vectorDifference(vector2), vectorOf(3f, 1f, 4f))

        vector1.vectorAddition(vector2)
        assertEquals(vector1, vectorOf(7f, 5f, 10f))
        vector1.vectorSubtraction(vector2)
        assertEquals(vector1, vectorOf(5f, 3f, 7f))
    }

    // <-- Test operator overloading -->

    @Test
    fun testJuicyScalarOperations() {
        assertEquals(vectorOf(4f, 3f, 3f) * 1.5f, vectorOf(6f, 4.5f, 4.5f))
        assertEquals(1.5f * vectorOf(4f, 3f, 3f), vectorOf(6f, 4.5f, 4.5f))
        assertEquals(vectorOf(6f, 12f) / 3f, vectorOf(2f, 4f))

        val vector = vectorOf(1f, 4f, 2f)
        vector *= 6f
        assertEquals(vector, vectorOf(6f, 24f, 12f))
        vector /= 3f
        assertEquals(vector, vectorOf(2f, 8f, 4f))
    }

    @Test
    fun testJuicyVectorOperations() {
        val vector1 = vectorOf(5f, 3f, 7f)
        val vector2 = vectorOf(2f, 2f, 3f)

        assertEquals(vector1 * vector2, 37f)
        assertEquals(vector1 + vector2, vectorOf(7f, 5f, 10f))
        assertEquals(vector1 - vector2, vectorOf(3f, 1f, 4f))

        vector1 += vector2
        assertEquals(vector1, vectorOf(7f, 5f, 10f))
        vector1 -= vector2
        assertEquals(vector1, vectorOf(5f, 3f, 7f))

        assertEquals(-vector2, vectorOf(-2f, -2f, -3f))
    }
}