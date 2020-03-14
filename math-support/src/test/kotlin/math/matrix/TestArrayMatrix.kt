package math.matrix

import org.junit.Assert.assertEquals
import org.junit.Test

class TestArrayMatrix {

    @Test
    fun testZeros() {
        val matrix = arrayZeroMatrix(2, 1)
        assertEquals(2, matrix.getNumRows())
        assertEquals(1, matrix.getNumCols())
        assertEquals(0f, matrix[0][0])
        assertEquals(0f, matrix[1][0])
    }

    @Test
    fun testIdentity() {
        val matrix = arrayIdentityMatrix(2)
        assertEquals(2, matrix.getNumRows())
        assertEquals(2, matrix.getNumCols())
        assertEquals(1f, matrix[0][0])
        assertEquals(0f, matrix[0][1])
        assertEquals(0f, matrix[1][0])
        assertEquals(1f, matrix[1][1])
    }

    @Test
    fun testMatrixOf() {
        val matrix = arrayMatrixOf(
                arrayOf(2f, 5f),
                arrayOf(1f, 3f),
                arrayOf(0f, -6f)
        )
        assertEquals(3, matrix.getNumRows())
        assertEquals(2, matrix.getNumCols())
        assertEquals(2f, matrix[0][0])
        assertEquals(5f, matrix[0][1])
        assertEquals(1f, matrix[1][0])
        assertEquals(3f, matrix[1][1])
        assertEquals(0f, matrix[2][0])
        assertEquals(-6f, matrix[2][1])
    }

    @Test
    fun testGetSet() {
        val matrix = arrayZeroMatrix(2,2)
        matrix.set(0, 1, 3f)
        assertEquals(3f, matrix[0][1])
        assertEquals(3f, matrix.get(0, 1))
        matrix[1][0] = 2f
        assertEquals(2f, matrix.get(1, 0))
    }
}