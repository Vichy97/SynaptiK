package neural_network

import functions.ActivationFunction
import functions.CostFunction
import org.la4j.Matrix
import org.la4j.Vector
import java.util.*
import kotlin.collections.ArrayList

class NeuralNetwork(private val learningRate: Double,
                    private val costFunction: CostFunction) {

    private lateinit var inputLayer: InputLayer
    private var layers: MutableList<HiddenLayer> = mutableListOf()
    private val random: Random = Random()

    fun addInputLayer(numberOfNodes: Int) {
        inputLayer = InputLayer(numberOfNodes)
    }

    fun addLayer(numberOfNodes: Int,  activationFunction: ActivationFunction) {
        layers.add(HiddenLayer(numberOfNodes, activationFunction))
    }

    fun initialize() {
        initializeWeights()
    }

    private fun initializeWeights() {
        initializeWeights(inputLayer, layers[0])
        for (i in 1 until layers.size) {
            initializeWeights(layers[i - 1], layers[i])
        }
    }

    private fun initializeWeights(previousLayer: Layer, layer: HiddenLayer) {
        layer.weightMatrix = Matrix.zero(layer.numberOfNeurons, previousLayer.numberOfNeurons)

        for (i in 0 until layer.weightMatrix.rows()) {
            for (j in 0 until layer.weightMatrix.columns()) {
                layer.weightMatrix.set(i, j, random.nextGaussian())
            }
            layer.biasVector[i] = 1.0
        }
    }

    fun feedForward(inputVector: Vector, expected: Vector) {
        println("input: " + inputVector.toCSV())

        inputLayer.activationVector = inputVector
        feedForward(inputLayer, layers[0])

        for (i in 1 until layers.size) {
            feedForward(layers[i - 1], layers[i])
        }
        backPropagate(expected)

        println("output: " + layers.last().activationVector.toCSV())
    }

    private fun feedForward(previousLayer: Layer, layer: HiddenLayer) {
        layer.zVector = layer.weightMatrix.multiply(previousLayer.activationVector).add(layer.biasVector)
        //apply activation function to each neuron
        for (i in 0 until layer.numberOfNeurons) {
            layer.activationVector[i] = layer.activationFunction.activate(layer.zVector[i])
            println("activating ${layer.activationVector[i]}")
        }
    }

    private fun backPropagate(expected: Vector) {
        val updatedLayers = mutableListOf<HiddenLayer>()

        var deltaVector = getOutputDeltaVector(expected, layers.last())
        for (i in layers.size - 1 downTo 0) {
            val layer = layers[i]
            val previousLayer = layers.getOrNull(i - 1) ?: inputLayer
            updatedLayers.add(backPropagate(deltaVector, previousLayer, layer))
            deltaVector = getDeltaVector(deltaVector, previousLayer, layer)
        }

        this.layers = updatedLayers.asReversed()
    }

    private fun backPropagate(deltaVector: Vector, previousLayer: Layer, layer: HiddenLayer): HiddenLayer {
        val previousActivationLayer = previousLayer.activationVector

        val newBiasVector = deltaVector.copy()
        val newWeightMatrix = deltaVector.outerProduct(previousActivationLayer)

        val updatedLayer = HiddenLayer(layer.numberOfNeurons, layer.activationFunction)
        updatedLayer.biasVector = newBiasVector
        updatedLayer.weightMatrix = newWeightMatrix
        updatedLayer.activationVector = layer.activationVector

        return updatedLayer
    }

    private fun getOutputDeltaVector(expected: Vector, layer: HiddenLayer): Vector {
        return costFunction.costPrime(getActivationPrimeVector(layer), expected)
    }

    private fun getDeltaVector(nextDeltaVector: Vector, previousLayer: Layer, layer: HiddenLayer): Vector {
        lateinit var deltaVector: Vector

        return deltaVector
    }

    private fun getActivationPrimeVector(layer: HiddenLayer): Vector {
        val outputVector: Vector = Vector.zero(layer.zVector.length())

        for (i in 0 until layer.zVector.length()) {
            outputVector[i] = layer.activationFunction.activatePrime(layer.zVector[i])
        }

        return outputVector
    }
}