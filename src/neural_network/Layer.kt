package neural_network

import Jama.Matrix

abstract class Layer(val numberOfNeurons: Int) {

    var neuronVector: Matrix = Matrix(numberOfNeurons, 1)

    abstract fun activate()
}