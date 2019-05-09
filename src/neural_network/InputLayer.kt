package neural_network

class InputLayer(numberOfNeurons: Int) : Layer(numberOfNeurons) {

    override fun activate() {
        //no-op
    }

    fun feedForward(input: DoubleArray) {
        for (i in 0 until input.size) {
            neuronVector.set(i, 0, input[i])
        }
    }
}