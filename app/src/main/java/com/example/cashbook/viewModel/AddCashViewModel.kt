package com.example.cashbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cashbook.CashBookRepository
import androidx.databinding.Observable
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashbook.database.CashBookDatabase
import com.example.cashbook.database.CashFlowEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddCashViewModel(private val repository: CashBookRepository, application: Application, statusData: Int) :
AndroidViewModel(application), Observable{
    val daoCash = CashBookDatabase.getInstance(application).cashFlowDatabaseDao
    private val _navigatetoHome = MutableLiveData<Boolean>()
    var statusFlow = ""
    val dataStatus = statusData

    @Bindable
    val inputDate = MutableLiveData<String>()

    @Bindable
    val inputNominal = MutableLiveData<String>()

    @Bindable
    val inputDescription = MutableLiveData<String>()

    val navigatetoHome: LiveData<Boolean>
        get() = _navigatetoHome

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _errorToast = MutableLiveData<Boolean>()

    val errotoast: LiveData<Boolean>
        get() = _errorToast

    private val _successToast = MutableLiveData<Boolean>()
    val successtoast: LiveData<Boolean>
        get() = _successToast

    fun simpanButton() {
        if(inputDate == null || inputNominal == null || inputDescription == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
                if(dataStatus == 1){
                    statusFlow = "pemasukan"
                } else {
                    statusFlow = "pengeluaran"
                }
                val cashFlowEntity = CashFlowEntity(date = inputDate.value.toString(), nominal = inputNominal.value.toString().toInt(), description = inputDescription.value.toString(), status = statusFlow)
                repository.insert(cashFlowEntity)
                inputDate.value = null
                inputNominal.value = null
                inputDescription.value = null
                _successToast.value = true
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