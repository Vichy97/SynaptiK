package neural_network

import functions.ActivationFunction
import org.la4j.Matrix
import org.la4j.Vector

class HiddenLayer constructor(
    numberOfNeurons: Int,
    val activationFunction: ActivationFunction
) : Layer(numberOfNeurons) {

    var zVector: Vector = Vector.zero(numberOfNeurons)
    var biasVector: Vector = Vector.zero(numberOfNeurons)
    lateinit var weightMatrix: Matrix
}