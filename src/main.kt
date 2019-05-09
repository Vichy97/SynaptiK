import data_processing.DataParser
import data_processing.DataScaler
import data_processing.DataUtils
import neural_network.NeuralNetwork

fun main() {
    val dataParser = DataParser()
    val scaler = DataScaler.NormalScaler

    var dataSet = dataParser.parseFile("Churn_Modelling.csv", intArrayOf(3, 6, 7, 8, 9, 10, 11, 12))
    DataUtils.printDataSet(dataSet)
    dataSet = scaler.scale(dataSet)

    val nn = NeuralNetwork()
    nn.addInputLayer(8)
    nn.addLayer(5, ActivationFunction.Relu)
    nn.addLayer(5, ActivationFunction.Relu)
    nn.addLayer(1, ActivationFunction.Sigmoid)

    nn.feedForward(dataSet[0])
}