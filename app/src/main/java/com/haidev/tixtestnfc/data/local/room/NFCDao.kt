package com.haidev.tixtestnfc.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.haidev.tixtestnfc.data.local.entity.NFCEntity

@Dao
interface NFCDao {
    @Query("SELECT * FROM nfc")
    fun getAllNFC(): LiveData<List<NFCEntity>>

    @Query("SELECT * FROM nfc WHERE serial_number = :serialNumber")
    fun getNFC(serialNumber: String): NFCEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNFC(nfcData: NFCEntity)

    @Update
    suspend fun updateNFC(nfcData: NFCEntity)

    @Delete
    fun deleteNFC(nfcData: NFCEntity)

}