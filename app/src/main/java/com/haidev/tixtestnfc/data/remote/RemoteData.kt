package com.haidev.tixtestnfc.data.remote

import android.content.Context
import com.haidev.tixtestnfc.data.remote.dto.NFCDataRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RemoteData @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext val context: Context
) : RemoteDataSource {
    override suspend fun doPostNFC(nfcDataRequest: NFCDataRequest) {
        apiService.postNFC(nfcDataRequest)
    }

    override suspend fun doPutNFC(serialNumber: String, nfcDataRequest: NFCDataRequest) {
        apiService.putNFC(serialNumber, nfcDataRequest)
    }

    override suspend fun doDeleteNFC(serialNumber: String) {
        apiService.deleteNFC(serialNumber)
    }
}