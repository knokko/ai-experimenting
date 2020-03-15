package math.matrix

import math.Num
import org.junit.Assert
import org.junit.Test

abstract class TestMatrix {

    abstract fun zeroMatrix(numRows: Int, numCols: Int): Matrix

    abstract fun identityMatrix(size: Int): Matrix

    abstract fun matrixOf(vararg elements: Array<Num>): Matrix

    @Test
    fun testZeros() {
        val matrix = zeroMatrix(2, 1)
        Assert.assertEquals(2, matrix.getNumRows())
        Assert.assertEquals(1, matrix.getNumCols())
        Assert.assertEquals(0f, matrix[0][0])
        Assert.assertEquals(0f, matrix[1][0])
    }

    @Test
    fun testIdentity() {
        val matrix = identityMatrix(2)
        Assert.assertEquals(2, matrix.getNumRows())
        Assert.assertEquals(2, matrix.getNumCols())
        Assert.assertEquals(1f, matrix[0][0])
        Assert.assertEquals(0f, matrix[0][1])
        Assert.assertEquals(0f, matrix[1][0])
        Assert.assertEquals(1f, matrix[1][1])
    }

    @Test
    fun testMatrixOf() {
        val matrix = matrixOf(
                arrayOf(2f, 5f),
                arrayOf(1f, 3f),
                arrayOf(0f, -6f)
        )
        Assert.assertEquals(3, matrix.getNumRows())
        Assert.assertEquals(2, matrix.getNumCols())
        Assert.assertEquals(2f, matrix[0][0])
        Assert.assertEquals(5f, matrix[0][1])
        Assert.assertEquals(1f, matrix[1][0])
        Assert.assertEquals(3f, matrix[1][1])
        Assert.assertEquals(0f, matrix[2][0])
        Assert.assertEquals(-6f, matrix[2][1])
    }

    @Test
    fun testGetSet() {
        val matrix = zeroMatrix(2,2)
        matrix.set(0, 1, 3f)
        Assert.assertEquals(3f, matrix[0][1])
        Assert.assertEquals(3f, matrix.get(0, 1))
        matrix[1][0] = 2f
        Assert.assertEquals(2f, matrix.get(1, 0))
    }

    @Test
    fun testEquals() {
        val first = zeroMatrix(2, 3)
        val second = zeroMatrix(2, 3)
        val third = identityMatrix(3)
        Assert.assertTrue(first == second)
        Assert.assertFalse(first == third)
    }

    @Test
    fun testClone() {
        val original = identityMatrix(2)
        val clone = original.clone()
        Assert.assertTrue(original == clone)
        Assert.assertFalse(original === clone)

        clone[0][0] = 2f
        Assert.assertEquals(1f, original[0][0])
    }

    @Test
    fun testAsVector() {
        val matrix = matrixOf(
                arrayOf(1f, 2f),
                arrayOf(3f, 4f)
        )
        val vector = matrix.asVector()
        Assert.assertEquals(1f, vector[0])
        Assert.assertEquals(2f, vector[1])
        Assert.assertEquals(3f, vector[2])
        Assert.assertEquals(4f, vector[3])

        vector[1] = 6f
        Assert.assertEquals(6f, matrix[0][1])
    }
}