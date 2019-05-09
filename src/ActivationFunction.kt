import kotlin.math.exp

sealed class ActivationFunction {
    abstract fun activate(x: Double): Double

    object Sigmoid : ActivationFunction() {
        override fun activate(x: Double): Double {
            return 1.0 / (1.0 + exp(-x))
        }
    }

    object Relu : ActivationFunction() {
        override fun activate(x: Double): Double {
            if (x > 0) {
                return x
            }
            return 0.0
        }
    }

    object None : ActivationFunction() {
        override fun activate(x: Double): Double {
            return x
        }
    }
}

