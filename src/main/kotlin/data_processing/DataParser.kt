package data_processing

import com.opencsv.CSVReader
import java.io.BufferedReader
import java.io.FileReader

class DataParser {

    fun parseFile(filePath: String, rows: IntArray): Array<DoubleArray> {
        println("parsing records...")
        val fileReader = BufferedReader(FileReader(filePath))
        val csvReader = CSVReader(fileReader)

        val records: ArrayList<DoubleArray> = arrayListOf()

        var record = csvReader.readNext()
        while (record != null) {
            records.add(removeUnusedRows(record, rows))
            record = csvReader.readNext()
        }
        println("parsing successful")
        return records.toTypedArray()
    }

    private fun removeUnusedRows(record: Array<String>, rows: IntArray): DoubleArray {
        val processedRecord = arrayListOf<Double>()

        for (i in 0 until record.size) {
            if (!rows.contains(i)) {
                continue
            }

            val value = record[i].toDoubleOrNull() ?: continue
            processedRecord.add(value)
        }

        return processedRecord.toDoubleArray()
    }

}