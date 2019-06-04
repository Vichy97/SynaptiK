package functions

import kotlin.math.exp

sealed class ActivationFunction {

    abstract fun activate(x: Double): Double

    abstract fun activatePrime(x: Double): Double

    object Sigmoid : ActivationFunction() {

        override fun activate(x: Double): Double {
            return 1.0 / (1.0 + exp(-x))
        }

        override fun activatePrime(x: Double): Double {
            return activate(x) * (1 - activate(x))
        }
    }

    object Relu : ActivationFunction() {
        override fun activate(x: Double): Double {
            if (x > 0) {
                return x
            }
            return 0.0
        }

        override fun activatePrime(x: Double): Double {
            if (x <= 0) {
                return 0.0
            }
            return 1.0
        }
    }

    object None : ActivationFunction() {
        override fun activate(x: Double): Double {
            return x
        }

        override fun activatePrime(x: Double): Double {
            return x
        }
    }
}

