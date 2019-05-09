package neural_network

import ActivationFunction
import Jama.Matrix
import java.util.*

class HiddenLayer(numberOfNeurons: Int,
                  val activationFunction: ActivationFunction,
                  val previousLayer: Layer) : Layer(numberOfNeurons) {

    //weights between this layer and the previous layer
    val weightMatrix: Matrix
    val biasVector: Matrix

    private val random: Random = Random()

    init {
        neuronVector = Matrix(numberOfNeurons, 1)
        biasVector = Matrix(numberOfNeurons, 1)
        weightMatrix = Matrix(numberOfNeurons, previousLayer.numberOfNeurons)

        for (i in 0 until weightMatrix.rowDimension) {
            for (j in 0 until weightMatrix.columnDimension) {
                weightMatrix.set(i, j, random.nextGaussian())
            }
            biasVector.set(i, 0, 1.0)
        }
    }

    override fun activate() {
        neuronVector = weightMatrix.times(previousLayer.neuronVector).plus(biasVector)
        //apply activation function to each neuron
        for (i in 0 until numberOfNeurons) {
            neuronVector.set(i, 0, activationFunction.activate(neuronVector.get(i, 0)))
            println("activating ${neuronVector.get(i, 0)}")
        }
    }
}