package math.vector

import math.Num
import org.junit.Assert
import org.junit.Test

abstract class TestVector {

    abstract fun zeroVector(size: Int): Vector

    abstract fun onesVector(size: Int): Vector

    abstract fun vectorOf(vararg elements: Num): Vector

    @Test
    fun testZeros() {
        val vector = zeroVector(2)
        Assert.assertEquals(2, vector.size())
        Assert.assertEquals(0f, vector[0])
        Assert.assertEquals(0f, vector[1])
    }

    @Test
    fun testOnes() {
        val vector = onesVector(2)
        Assert.assertEquals(2, vector.size())
        Assert.assertEquals(1f, vector[0])
        Assert.assertEquals(1f, vector[1])
    }

    @Test
    fun testVectorOf() {
        val vector = vectorOf(1f,2f,3f,4f)
        Assert.assertEquals(4, vector.size())
        Assert.assertEquals(1f, vector[0])
        Assert.assertEquals(2f, vector[1])
        Assert.assertEquals(3f, vector[2])
        Assert.assertEquals(4f, vector[3])
    }

    @Test
    fun testSet() {
        val vector = zeroVector(2)
        vector[0] = 4f
        vector[1] = -2f
        Assert.assertEquals(4f, vector[0])
        Assert.assertEquals(-2f, vector[1])
    }

    @Test
    fun testIterator() {
        val vector = vectorOf(3f, 1f, 7f, 0f)
        for ((index, value) in vector.withIndex())
            Assert.assertEquals(vector[index], value)

        Assert.assertEquals(11f, vector.sum())
    }

    @Test
    fun testCopy() {
        val source = vectorOf(3f, 1f)
        val dest = zeroVector(3)
        source.copy(dest)
        Assert.assertEquals(3f, dest[0])
        Assert.assertEquals(1f, dest[1])
        Assert.assertEquals(0f, dest[2])
    }

    @Test
    fun testAsMatrix() {
        val vector = vectorOf(2f, 1f, 4f, 3f, 0f, 8f)
        val matrix = vector.asMatrix(2, 3)
        Assert.assertEquals(2f, matrix[0][0])
        Assert.assertEquals(1f, matrix[0][1])
        Assert.assertEquals(4f, matrix[0][2])
        Assert.assertEquals(3f, matrix[1][0])
        Assert.assertEquals(0f, matrix[1][1])
        Assert.assertEquals(8f, matrix[1][2])

        matrix[0][0] = 1f
        Assert.assertEquals(1f, vector[0])
    }

    @Test
    fun testEquals() {
        val first = vectorOf(1f, 3f)
        val second = vectorOf(1f, 3f)
        val third = vectorOf(2f, 3f)
        Assert.assertTrue(first == second)
        Assert.assertFalse(second == third)
    }

    @Test
    fun testClone() {
        val vector = vectorOf(-3f, 4f)
        val cloned = vector.clone()
        Assert.assertTrue(vector == cloned)
        Assert.assertFalse(vector === cloned)

        vector[0] = 1f
        Assert.assertEquals(-3f, cloned[0])
    }
}