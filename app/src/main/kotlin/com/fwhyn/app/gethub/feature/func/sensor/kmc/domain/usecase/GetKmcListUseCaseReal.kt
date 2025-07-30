package com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.usecase

import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.repository.KmcDataRepository
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.helper.toData
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.helper.toDomain
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.GetKmcDomainParam
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.KmcDomain
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetKmcListUseCaseReal @Inject constructor(
    private val kmcDataRepository: KmcDataRepository,
) : GetKmcListUseCase() {

    private var kmcDomainList = listOf<KmcDomain>()

    override suspend fun onRunning(
        param: GetKmcDomainParam,
        result: suspend (List<KmcDomain>) -> Unit,
    ) {
        while (param.isRealTime) {
            result(getKmcDomainList(param))

            delay(1000)
        }

        // If not in real-time mode, just fetch the latest data once
        result(getKmcDomainList(param))
    }

    private suspend fun getKmcDomainList(param: GetKmcDomainParam): List<KmcDomain> {
        val kmcData = kmcDataRepository.get(param.toData()).toDomain()
        val kmcList = ArrayList(kmcDomainList)

        kmcList.add(0, kmcData)

        if (kmcList.size > param.maxDataList) {
            kmcList.removeAt(kmcList.size - 1)
        }

        kmcDomainList = kmcList

        return kmcList
    }
}