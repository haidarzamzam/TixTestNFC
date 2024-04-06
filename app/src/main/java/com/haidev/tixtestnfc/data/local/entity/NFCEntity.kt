package com.haidev.tixtestnfc.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nfc")
data class NFCEntity(
    @field:ColumnInfo(name = "serial_number")
    @field:PrimaryKey
    val serialNumber: String = "",

    @field:ColumnInfo(name = "message")
    val message: String = ""
)