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
        inputLayer.activationVector = inputVector
        feedForward(inputLayer, layers[0])

        for (i in 1 until layers.size) {
            feedForward(layers[i - 1], layers[i])
        }
        backPropagate(expected)
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
        val updatedLayers = ArrayList<HiddenLayer>(layers.size)

        var outputBiasVector: Vector = layers.last().biasVector.copy()
        var outputWeightMatrix: Matrix = layers.last().weightMatrix.copy()

        val deltaVector = costFunction.costPrime(layers.last().activationVector, expected)
            .hadamardProduct(getActivationPrimeVector(layers.last()))
        outputBiasVector.add(deltaVector.multiply(learningRate))
        val secondToLastLayer = layers[layers.lastIndex - 1]
        for (j in 0 until outputWeightMatrix.rows()) {
            for (k in 0 until outputWeightMatrix.columns()) {
                outputWeightMatrix[j, k] += (secondToLastLayer.activationVector[j] + (deltaVector[j] * learningRate))
            }
        }
        val outputLayer = HiddenLayer(layers.last().numberOfNeurons, layers.last().activationFunction)
        updatedLayers[layers.lastIndex] = outputLayer

        for (l in layers.size - 1 downTo 0) {
            val activationPrimeVector = getActivationPrimeVector(layers[l])
            outputWeightMatrix = layers[l].weightMatrix.copy()

            for (j in 0 until outputWeightMatrix.rows()) {
                for (k in 0 until outputWeightMatrix.columns()) {
                    outputWeightMatrix[j, k] += (secondToLastLayer.activationVector[j] + (deltaVector[j] * learningRate))
                }
            }
            val hiddenLayer = HiddenLayer(layers[l].numberOfNeurons, layers[l].activationFunction)
            updatedLayers[l] = hiddenLayer
        }
        layers = updatedLayers
    }

    private fun getActivationPrimeVector(layer: HiddenLayer): Vector {
        val outputVector: Vector = Vector.zero(layer.zVector.length())

        for (i in 0 until layer.zVector.length()) {
            outputVector[i] = layer.activationFunction.activatePrime(layer.zVector[i])
        }

        return outputVector
    }
}