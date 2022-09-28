package com.example.cashbook.viewModel

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import com.example.cashbook.CashBookRepository
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashbook.database.CashBookDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SettingViewModel(private val repository: CashBookRepository, application: Application) :
AndroidViewModel(application), Observable{
    val dao = CashBookDatabase.getInstance(application).userDatabaseDao
    private val _navigatetoHome = MutableLiveData<Boolean>()

    @Bindable
    val inputCurrentPassword = MutableLiveData<String>()

    @Bindable
    val inputNewPassword = MutableLiveData<String>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val navigatetoHome: LiveData<Boolean>
        get() = _navigatetoHome

    private val _errorToast = MutableLiveData<Boolean>()

    val errotoast: LiveData<Boolean>
        get() = _errorToast

    private val _successToast = MutableLiveData<Boolean>()
    val successtoast: LiveData<Boolean>
    get() = _successToast

    fun simpanButton() {
        if(inputCurrentPassword == null || inputNewPassword == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
                val getUserPassword = repository.getPassword(inputCurrentPassword.value!!)
                if(getUserPassword != null) {
                    getUserPassword.password = inputNewPassword.value.toString()
                    repository.updatePassword(getUserPassword)
                    inputCurrentPassword.value = null
                    inputNewPassword.value = null
                    _successToast.value = true
                } else {
                    _errorToast.value = true
                }
            }
        }
    }

    fun backButton() {
        _navigatetoHome.value = true
    }

    fun doneNavigatingHome() {
        _navigatetoHome.value = false
    }

    fun donetoast() {
        _errorToast.value = false
        _successToast.value = false
        Log.i("MYTAG", "Done taoasting ")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}