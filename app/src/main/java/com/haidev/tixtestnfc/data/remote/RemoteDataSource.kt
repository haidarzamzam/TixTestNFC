package com.haidev.tixtestnfc.data.remote

import com.haidev.tixtestnfc.data.remote.dto.NFCDataRequest

interface RemoteDataSource {
    suspend fun doPostNFC(nfcDataRequest: NFCDataRequest)

    suspend fun doPutNFC(serialNumber: String, nfcDataRequest: NFCDataRequest)

    suspend fun doDeleteNFC(serialNumber: String)
}