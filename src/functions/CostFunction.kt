package functions

import org.la4j.Vector


sealed class CostFunction {

    abstract fun cost(actual: Vector, expected: Vector): Vector

    abstract fun costPrime(actual: Vector, expected: Vector): Vector

    object DiffSquare : CostFunction() {

        override fun cost(actual: Vector, expected: Vector): Vector {
            val outputVector = Vector.zero(actual.length())
            for (i in 0 until actual.length()) {
                outputVector[i] =  0.5 * Math.pow((actual[i] - expected[i]), 2.0)
            }
            return outputVector
        }

        override fun costPrime(actual: Vector, expected: Vector): Vector {
            val outputVector = Vector.zero(actual.length())
            for (i in 0 until actual.length()) {
                outputVector[i] =  (actual[i] - expected[i])
            }
            return outputVector
        }

    }
}