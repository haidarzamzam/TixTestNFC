package com.haidev.tixtestnfc.data

import androidx.lifecycle.LiveData
import com.haidev.tixtestnfc.data.local.entity.NFCEntity
import com.haidev.tixtestnfc.data.local.room.NFCDao
import javax.inject.Inject

class NFCRepository @Inject constructor(
    private val nfcDao: NFCDao
) {
    fun getAllFC(): LiveData<List<NFCEntity>> {
        return nfcDao.getAllNFC()
    }

    fun getNFC(serialNumber: String): NFCEntity {
        return nfcDao.getNFC(serialNumber)
    }

    fun insertNFC(nfcEntity: NFCEntity) {
        nfcDao.insertNFC(nfcEntity)
    }

    fun deleteNFC(nfcEntity: NFCEntity) {
        nfcDao.deleteNFC(nfcEntity)
    }

    suspend fun updateNFC(nfcEntity: NFCEntity) {
        nfcDao.updateNFC(nfcEntity)
    }
}