package math.matrix

import org.junit.Assert.*
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

    @Test
    fun testEquals() {
        val first = arrayZeroMatrix(2, 3)
        val second = arrayZeroMatrix(2, 3)
        val third = arrayIdentityMatrix(3)
        assertTrue(first == second)
        assertFalse(first == third)
    }

    @Test
    fun testClone() {
        val original = arrayIdentityMatrix(2)
        val clone = original.clone()
        assertTrue(original == clone)
        assertFalse(original === clone)

        clone[0][0] = 2f
        assertEquals(1f, original[0][0])
    }

    @Test
    fun testAsVector() {
        val matrix = arrayMatrixOf(
                arrayOf(1f, 2f),
                arrayOf(3f, 4f)
        )
        val vector = matrix.asVector()
        assertEquals(1f, vector[0])
        assertEquals(2f, vector[1])
        assertEquals(3f, vector[2])
        assertEquals(4f, vector[3])

        vector[1] = 6f
        assertEquals(6f, matrix[0][1])
    }
}