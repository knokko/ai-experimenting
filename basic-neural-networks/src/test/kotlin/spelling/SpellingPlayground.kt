package spelling

fun main() {

    val wordMatrix = wordMatrix("test", "hello", "pretty", "bye")

    val queryVector = wordQueryVector("teset")
    val targetVector = wordMatrix * queryVector
    val result = wordFromTargetVector(targetVector, 2f)

    println("result is $result")
}