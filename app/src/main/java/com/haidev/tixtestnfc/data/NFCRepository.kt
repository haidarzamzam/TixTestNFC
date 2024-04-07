package com.haidev.tixtestnfc.data

import androidx.lifecycle.LiveData
import com.haidev.tixtestnfc.data.local.entity.NFCEntity
import com.haidev.tixtestnfc.data.local.room.NFCDao
import com.haidev.tixtestnfc.data.remote.RemoteData
import com.haidev.tixtestnfc.data.remote.dto.NFCDataRequest
import javax.inject.Inject

class NFCRepository @Inject constructor(
    private val nfcDao: NFCDao,
    private val remoteData: RemoteData,
) {
    fun getAllNFCLocal(): LiveData<List<NFCEntity>> {
        return nfcDao.getAllNFC()
    }

    fun getNFCLocal(serialNumber: String): NFCEntity {
        return nfcDao.getNFC(serialNumber)
    }

    fun insertNFCLocal(nfcEntity: NFCEntity) {
        nfcDao.insertNFC(nfcEntity)
    }

    fun deleteNFCLocal(nfcEntity: NFCEntity) {
        nfcDao.deleteNFC(nfcEntity)
    }

    suspend fun updateNFCLocal(nfcEntity: NFCEntity) {
        nfcDao.updateNFC(nfcEntity)
    }

    suspend fun postNFCRemote(nfcDataRequest: NFCDataRequest) {
        remoteData.doPostNFC(nfcDataRequest)
    }

    suspend fun putNFCRemote(serialNumber: String, nfcDataRequest: NFCDataRequest) {
        remoteData.doPutNFC(serialNumber, nfcDataRequest)
    }

    suspend fun deleteNFCRemote(serialNumber: String) {
        remoteData.doDeleteNFC(serialNumber)
    }
}