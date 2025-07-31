package com.fwhyn.app.gethub.feature.screen.home

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import com.fwhyn.app.gethub.common.helper.emitEvent
import com.fwhyn.app.gethub.common.storage.saf.SafUtil
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.ExportKmcListParam
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.GetKmcDomainParam
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.usecase.ExportKmcListUseCase
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.usecase.GetKmcListUseCase
import com.fwhyn.app.gethub.feature.screen.home.component.HomeMessageCode
import com.fwhyn.app.gethub.feature.screen.home.helper.OpenSafCode
import com.fwhyn.app.gethub.feature.screen.home.helper.extension.toDomain
import com.fwhyn.app.gethub.feature.screen.home.helper.extension.toUi
import com.fwhyn.app.gethub.feature.screen.home.model.HomeEvent
import com.fwhyn.app.gethub.feature.screen.home.model.HomeProperties
import com.fwhyn.app.gethub.feature.screen.home.model.HomeState
import com.fwhyn.app.gethub.feature.screen.home.model.KmcUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getKmcListUseCase: GetKmcListUseCase,
    private val exportKmcListUseCase: ExportKmcListUseCase,
) : HomeVmInterface() {

    private val scope: CoroutineScope
        get() = viewModelScope

    private val event: MutableSharedFlow<HomeEvent> = MutableSharedFlow()
    private val state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Idle)
    private val isRealTimeData: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val kmcUiList: MutableStateFlow<List<KmcUi>> = MutableStateFlow(emptyList())

    // ----------------------------------------------------------------
    override val properties: HomeProperties = HomeProperties(
        event = event,
        state = state,
        isRealTimeData = isRealTimeData,
        kmcUiList = kmcUiList
    )

    // ----------------------------------------------------------------
    init {
        getKmcUseCase(GetKmcDomainParam.default())
    }

    // ----------------------------------------------------------------
    override fun onConnectOrDisconnect() {
        isRealTimeData.value = !isRealTimeData.value
        getKmcUseCase(GetKmcDomainParam.default(isRealTime = isRealTimeData.value))
    }

    override fun onExportData() {
        event.emitEvent(scope, HomeEvent.OpenSaf(OpenSafCode.ExportKmcList))
    }

    override fun onCreateFileResult(result: ActivityResult) {
        checkForExportRequest(
            resultCode = result.resultCode,
            data = result.data
        )
    }

    // ----------------------------------------------------------------
    private fun getKmcUseCase(param: GetKmcDomainParam) {
        getKmcListUseCase.invoke(
            scope = scope,
            onFetchParam = { param },
            onOmitResult = { res ->
                res.onSuccess { kmcListDomain ->
                    kmcUiList.value = kmcListDomain.toUi()
                }.onFailure {
                    event.emit(HomeEvent.OnNotify(HomeMessageCode.GetKmcListError))
                }
            },
        )

    }

    private fun checkForExportRequest(
        resultCode: Int,
        data: Intent?,
    ) {
        val uri: Uri? = SafUtil.getGrantedPathOrNull(
            resultCode = resultCode,
            resultData = data
        )

        uri?.let {
            exportKmcListUseCase.invoke(
                scope = scope,
                onStart = { state.value = HomeState.Loading },
                onFetchParam = {
                    val kmcList = properties.kmcUiList.value
                    ExportKmcListParam(kmcList.toDomain(), uri)
                },
                onOmitResult = { res ->
                    res.onSuccess {
                        event.emit(HomeEvent.OnNotify(HomeMessageCode.ExportSuccess))
                    }.onFailure {
                        event.emit(HomeEvent.OnNotify(HomeMessageCode.ExportError))
                    }
                },
                onFinish = { state.value = HomeState.Idle }
            )
        }
    }
}