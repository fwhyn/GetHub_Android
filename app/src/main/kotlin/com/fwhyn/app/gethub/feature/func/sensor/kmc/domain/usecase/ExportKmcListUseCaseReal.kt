package com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.usecase

import android.content.Context
import android.net.Uri
import com.fwhyn.app.gethub.common.storage.saf.SafUtil
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.ExportKmcListParam
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.GenerateKmcListExcelParam
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.KmcDomain
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class ExportKmcListUseCaseReal @Inject constructor(
    @ApplicationContext private val context: Context,
    private val generateKmcListExcelUseCase: GenerateKmcListExcelUseCase,
) : ExportKmcListUseCase() {

    override suspend fun onRunning(param: ExportKmcListParam, result: suspend (Unit) -> Unit) {
        generateWorkbook(kmcList = param.kmcList) { workbook ->
            saveWorkbookToUri(workbook, param.dstPath)

            // Notify the result successfully
            result(Unit)
        }
    }

    private fun saveWorkbookToUri(
        workbook: XSSFWorkbook,
        dstPath: Uri,
    ) {
        SafUtil.saveFile(
            appContext = context,
            uri = dstPath
        ) { outStream ->
            workbook.write(outStream)
            workbook.close()
        }
    }

    private suspend fun generateWorkbook(
        kmcList: List<KmcDomain>,
        result: suspend (XSSFWorkbook) -> Unit,
    ) {
        generateKmcListExcelUseCase.invoke(
            scope = CoroutineScope(coroutineContext),
            onFetchParam = {
                GenerateKmcListExcelParam(kmcList = kmcList)
            },
            onOmitResult = { res ->
                res.onSuccess { workbook ->
                    result(workbook)
                }.onFailure { error ->
                    throw Exception(error)
                }
            }
        )
    }
}