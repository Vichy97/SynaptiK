package neural_network

import org.la4j.Vector

abstract class Layer(val numberOfNeurons: Int) {

    var activationVector: Vector = Vector.zero(numberOfNeurons)
}