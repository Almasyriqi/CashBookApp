package com.example.cashbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cashbook.CashBookRepository
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashbook.database.CashBookDatabase

class HomeViewModel(private val repository: CashBookRepository, application: Application) :
AndroidViewModel(application), Observable{

    val dao = CashBookDatabase.getInstance(application).userDatabaseDao
    private val _navigatetoSetting = MutableLiveData<Boolean>()

    val navigatetoSetting: LiveData<Boolean>
        get() = _navigatetoSetting

    fun settingButton() {
        _navigatetoSetting.value = true
    }

    fun doneNavigatingSetting() {
        _navigatetoSetting.value = false
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}