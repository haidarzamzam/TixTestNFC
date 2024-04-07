package com.haidev.tixtestnfc.data.remote.dto

data class NFCDataResponse(
    val statusCode: Int,
    val statusMessage: String,
    val statusSuccess: String,
    val data: DataNFC
) {
    data class DataNFC(
        val serialNumber: String,
        val message: String
    )
}