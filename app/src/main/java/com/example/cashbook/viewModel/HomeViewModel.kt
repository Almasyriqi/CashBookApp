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

    private val _navigatetoPemasukan = MutableLiveData<Boolean>()
    val navigatetoPemasukan: LiveData<Boolean>
        get() = _navigatetoPemasukan

    private val _navigatetoPengeluaran = MutableLiveData<Boolean>()
    val navigatetoPengeluaran: LiveData<Boolean>
        get() = _navigatetoPengeluaran

    fun settingButton() {
        _navigatetoSetting.value = true
    }

    fun pemasukanButton() {
        _navigatetoPemasukan.value = true
    }

    fun pengeluaranButton() {
        _navigatetoPengeluaran.value = true
    }

    fun doneNavigatingAddCashFlow() {
        _navigatetoPemasukan.value = false
        _navigatetoPengeluaran.value = false
    }

    fun doneNavigatingSetting() {
        _navigatetoSetting.value = false
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}