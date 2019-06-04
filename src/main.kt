import data_processing.DataParser
import data_processing.DataScaler
import data_processing.DataUtils
import functions.ActivationFunction
import functions.CostFunction
import neural_network.NeuralNetwork
import org.la4j.Vector

fun main() {
    val dataParser = DataParser()
    val scaler = DataScaler.NormalScaler

    var dataSet = dataParser.parseFile("Churn_Modelling.csv", intArrayOf(3, 6, 7, 8, 9, 10, 11, 12))
    var resultSet = dataParser.parseFile("Churn_Modelling.csv", intArrayOf(13))
    DataUtils.printDataSet(dataSet)
    dataSet = scaler.scale(dataSet)

    val nn = NeuralNetwork(.01, CostFunction.DiffSquare)
    nn.addInputLayer(8)
    nn.addLayer(5, ActivationFunction.Relu)
    nn.addLayer(5, ActivationFunction.Relu)
    nn.addLayer(1, ActivationFunction.Sigmoid)

    nn.initialize()

    nn.feedForward(Vector.fromArray(dataSet[1]), Vector.fromArray(resultSet[1]))
}