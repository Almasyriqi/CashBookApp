package com.example.cashbook.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cashbook.CashBookRepository
import java.lang.IllegalArgumentException

class HomeViewModelFactory (
    private  val repository: CashBookRepository,
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}