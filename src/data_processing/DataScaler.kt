package data_processing


sealed class DataScaler {

    abstract fun scale(dataset: Array<DoubleArray>): Array<DoubleArray>

    object CenterScaler: DataScaler() {

        override fun scale(dataset: Array<DoubleArray>): Array<DoubleArray> {
            TODO("not implemented")
        }
    }

    object StandardScaler: DataScaler() {
        override fun scale(dataset: Array<DoubleArray>): Array<DoubleArray> {
            TODO("not implemented")
        }
    }

    object NormalScaler: DataScaler() {
        override fun scale(dataset: Array<DoubleArray>): Array<DoubleArray> {
            println("normalizing dataset...")
            val minMaxArray = DataUtils.findMinMax(dataset)

            for (i in 1 until dataset.size) {
                for (j in 0 until dataset[1].size) {
                    val minMax = minMaxArray[j]
                    val value = dataset[i][j]
                    val scaledValue = (value - minMax.min) / (minMax.max - minMax.min)
                    dataset[i][j] = scaledValue
                }
            }

            println("normalization complete!")
            return dataset
        }
    }
}

