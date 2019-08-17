import functions.ActivationFunction
import functions.CostFunction
import neural_network.NeuralNetwork
import org.la4j.Vector

fun main() {

    val nn = NeuralNetwork(.01, CostFunction.DiffSquare)
    nn.addInputLayer(2)
    nn.addLayer(2, ActivationFunction.Sigmoid)
    nn.addLayer(1, ActivationFunction.Sigmoid)

    nn.initialize()

    val dataset = listOf(
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 1.0),
        doubleArrayOf(1.0, 0.0),
        doubleArrayOf(1.0, 1.0)
    )

    val expectedOutputs = listOf(
        doubleArrayOf(0.0),
        doubleArrayOf(1.0),
        doubleArrayOf(1.0),
        doubleArrayOf(0.0)
    )

   dataset.forEachIndexed { index, doubles ->
       nn.feedForward(Vector.fromArray(doubles), Vector.fromArray(expectedOutputs[index]))
   }
}