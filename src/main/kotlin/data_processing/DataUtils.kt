package data_processing

object DataUtils {

    fun printDataSet(dataSet: Array<DoubleArray>) {
        for (row in dataSet) {
            for (column in row) {
                print(String.format("%12s", "%.3f".format(column)))
            }
            println()
        }
    }

    fun findMinMax(dataSet: Array<DoubleArray>): Array<MinMax> {
        println("calculating column min and max values...")
        val minMaxArray = Array(dataSet[1].size) {
            return@Array MinMax(Double.MAX_VALUE, Double.MIN_VALUE)
        }

        //loop rows
        for (i in 1 until dataSet.size) {

            //loop columns
            for (j in 0 until dataSet[i].size) {
                if (dataSet[i][j] < minMaxArray[j].min) {
                    minMaxArray[j].min = dataSet[i][j]
                }
                if (dataSet[i][j] > minMaxArray[j].max) {
                    minMaxArray[j].max = dataSet[i][j]
                }
            }
        }

        return minMaxArray
    }
}