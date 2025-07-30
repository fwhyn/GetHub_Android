package com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.usecase

import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.GenerateKmcListExcelParam
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.KmcDomain
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import javax.inject.Inject

class GenerateKmcListExcelUseCaseReal @Inject constructor() : GenerateKmcListExcelUseCase() {

    override suspend fun onRunning(param: GenerateKmcListExcelParam, result: suspend (XSSFWorkbook) -> Unit) {
        val _result = generateExcelWorkBook(param.kmcList)
        result(_result)
    }

    private fun generateExcelWorkBook(dataList: List<KmcDomain>): XSSFWorkbook {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("KmcData")

        // Header row
        val header = sheet.createRow(0)
        val data1 = dataList[0]
        header.createCell(0).setCellValue("Timestamp")
        header.createCell(1).setCellValue("SpO2 (" + data1.spoO2.unit + ")")
        header.createCell(2).setCellValue("Temperature (" + data1.temperature.unit + ")")
        header.createCell(3).setCellValue("Respiration Rate (" + data1.respirationRate.unit + ")")

        // Data rows
        dataList.forEachIndexed { index, data ->
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(data.timeStamp.toDouble())
            row.createCell(1).setCellValue(data.spoO2.data.toDouble())
            row.createCell(2).setCellValue(data.temperature.data.toDouble())
            row.createCell(3).setCellValue(data.respirationRate.data.toDouble())
        }

        return workbook
    }
}