package spelling

import math.Num
import math.matrix.Matrix
import math.matrix.arrayZeroMatrix
import math.vector.Vector
import math.vector.arrayZeroVector

const val WORD_LENGTH = 10

fun wordMatrix(vararg words: String): Matrix {
    val numRows = WORD_LENGTH * 26
    val numCols = 26 * 26

    val queryMatrix = arrayZeroMatrix(words.size, numCols)
    for ((wordIndex, word) in words.withIndex())
        queryMatrix[wordIndex] = wordQueryVector(word)

    val targetMatrix = arrayZeroMatrix(numRows, words.size)
    for ((wordIndex, word) in words.withIndex())
        targetMatrix.setCol(wordIndex, wordTargetVector(word))

    return targetMatrix * queryMatrix
}

fun wordQueryVector(word: String): Vector {
    val wordVector = arrayZeroVector(26 * 26)
    for (charIndex in 1 until word.length) {
        val firstChar = word[charIndex - 1]
        val secondChar = word[charIndex]
        if (firstChar < 'a' || firstChar > 'z' || secondChar < 'a' || secondChar > 'z')
            throw IllegalArgumentException("Only lowercase alphabet characters are allowed")
        wordVector[(firstChar - 'a') + 26 * (secondChar - 'a')] = 1f
    }
    return wordVector
}

fun wordFromTargetVector(targetVector: Vector, threshold: Num): String {
    var result = ""
    for ((index, value) in targetVector.withIndex())
        if (value >= threshold)
            result += 'a' + index % 26
    return result
}

private fun wordTargetVector(word: String): Vector {
    if (word.length > WORD_LENGTH)
        throw IllegalArgumentException("Word can be at most 10 letters long")

    val wordVector = arrayZeroVector(26 * WORD_LENGTH)
    for ((index, character) in word.withIndex()) {
        if (character < 'a' || character > 'z')
            throw IllegalArgumentException("Only lowercase alphabet characters are allowed")
        wordVector[26 * index + (character - 'a')] = 1f
    }

    return wordVector
}