package com.haidev.tixtestnfc.data.nfc

data class NdefRecordData(
    val tnf: Short,
    val type: ByteArray,
    val id: ByteArray,
    val payload: ByteArray
)