package com.haidev.tixtestnfc.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haidev.tixtestnfc.data.NFCRepository
import com.haidev.tixtestnfc.data.local.entity.NFCEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val newsRepository: NFCRepository) : ViewModel() {
    fun getAllNFC() = newsRepository.getAllFC()

    fun getNFC(serialNumber: String) = newsRepository.getNFC(serialNumber)

    fun insertNFC(nfcEntity: NFCEntity) {
        viewModelScope.launch {
            newsRepository.insertNFC(nfcEntity)
        }
    }

    fun deleteNFC(nfcEntity: NFCEntity) {
        viewModelScope.launch {
            newsRepository.deleteNFC(nfcEntity)
        }
    }

    fun updateNFC(nfcEntity: NFCEntity) {
        viewModelScope.launch {
            newsRepository.updateNFC(nfcEntity)
        }
    }
}