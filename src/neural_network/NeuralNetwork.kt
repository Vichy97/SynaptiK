package neural_network

import ActivationFunction

class NeuralNetwork {

    private val layers: ArrayList<Layer> = arrayListOf()

    fun addInputLayer(numberOfNodes: Int) {
        layers.add(InputLayer(numberOfNodes))
    }

    fun addLayer(numberOfNodes: Int,  activationFunction: ActivationFunction) {
        val lastLayer = layers[(layers.lastIndex)]
        layers.add(HiddenLayer(numberOfNodes, activationFunction, lastLayer))
    }

    fun feedForward(input: DoubleArray) {
        (layers[0] as InputLayer).feedForward(input)
        for (layer in layers) {
            layer.activate()
        }
    }
}