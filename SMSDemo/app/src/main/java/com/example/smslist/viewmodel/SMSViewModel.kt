package com.example.smslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.smslist.model.SMSTableModel
import com.example.smslist.repository.SMSRepository
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class SMSViewModel(private val repository: SMSRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allSMS: LiveData<List<SMSTableModel>> = repository.allSMS.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(smsText: SMSTableModel) = viewModelScope.launch {
        repository.insert(smsText)
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun updateLatestSMS(isSetFalse: Boolean, isTrue: Boolean) = viewModelScope.launch {
        repository.updateLatestSMS(isSetFalse,isTrue)
    }
}

class SMSViewModelFactory(private val repository: SMSRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SMSViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SMSViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
