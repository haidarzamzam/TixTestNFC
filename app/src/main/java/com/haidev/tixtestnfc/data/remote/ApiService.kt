package com.haidev.tixtestnfc.data.remote

import com.haidev.tixtestnfc.data.remote.dto.NFCDataRequest
import com.haidev.tixtestnfc.data.remote.dto.NFCDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("nfc")
    suspend fun postNFC(@Body nfcDataRequest: NFCDataRequest): Response<NFCDataResponse>

    @PUT("nfc/{serialNumber}")
    suspend fun putNFC(
        @Path("serialNumber") serialNumber: String,
        @Body nfcDataRequest: NFCDataRequest
    ): Response<NFCDataResponse>

    @DELETE("nfc/{serialNumber}")
    suspend fun deleteNFC(@Path("serialNumber") serialNumber: String): Response<NFCDataResponse>
}