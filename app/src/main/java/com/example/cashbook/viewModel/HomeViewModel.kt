package com.example.cashbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cashbook.CashBookRepository
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData

class HomeViewModel(private val repository: CashBookRepository, application: Application) :
AndroidViewModel(application), Observable{

    private val _navigatetoSetting = MutableLiveData<Boolean>()

    fun settingButton() {
        _navigatetoSetting.value = true
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}