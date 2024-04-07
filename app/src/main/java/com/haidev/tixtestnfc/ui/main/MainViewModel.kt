package com.haidev.tixtestnfc.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haidev.tixtestnfc.data.NFCRepository
import com.haidev.tixtestnfc.data.local.entity.NFCEntity
import com.haidev.tixtestnfc.data.remote.dto.NFCDataRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val newsRepository: NFCRepository) : ViewModel() {
    fun getAllNFC() = newsRepository.getAllNFCLocal()

    fun getNFC(serialNumber: String) = newsRepository.getNFCLocal(serialNumber)

    fun insertNFC(nfcEntity: NFCEntity) {
        viewModelScope.launch {
            newsRepository.insertNFCLocal(nfcEntity)
            try {
                newsRepository.postNFCRemote(
                    NFCDataRequest(
                        serialNumber = nfcEntity.serialNumber,
                        message = nfcEntity.message
                    )
                )
            } catch (e: Exception) {
                Log.d("MainViewModel", "postNFC: ${e.message}")
            }
        }
    }

    fun deleteNFC(nfcEntity: NFCEntity) {
        viewModelScope.launch {
            newsRepository.deleteNFCLocal(nfcEntity)
            try {
                newsRepository.deleteNFCRemote(nfcEntity.serialNumber)
            } catch (e: Exception) {
                Log.d("MainViewModel", "deleteNFC: ${e.message}")
            }
        }
    }

    fun updateNFC(nfcEntity: NFCEntity) {
        viewModelScope.launch {
            newsRepository.updateNFCLocal(nfcEntity)
            try {
                newsRepository.putNFCRemote(
                    nfcEntity.serialNumber,
                    NFCDataRequest(
                        serialNumber = nfcEntity.serialNumber,
                        message = nfcEntity.message
                    )
                )
            } catch (e: Exception) {
                Log.d("MainViewModel", "updateNFC: ${e.message}")
            }
        }
    }
}