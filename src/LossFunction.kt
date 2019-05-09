import kotlin.math.pow

sealed class LossFunction {

    abstract fun loss(predicted: Double, actual: Double): Double

    object SumOfSquares : LossFunction() {
        override fun loss(predicted: Double, actual: Double): Double {
            return (actual - predicted).pow(2)
        }
    }
}