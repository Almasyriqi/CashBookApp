package com.example.cashbook

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cashbook.database.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CashBookRepository(private val dao: UserDao, private val cashDao: CashFlowDao) {
    val users = dao.getAllUsers()

    val cashFlow = cashDao.getAllCashFlow()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getUserName(userName: String):UserEntity?{
        return dao.getUsername(userName)
    }

    fun getPassword(password: String):UserEntity?{
        return dao.getPassword(password)
    }

    fun updatePassword(user: UserEntity){
        executorService.execute { dao.updatePassword(user) }
    }

    fun insert(cash: CashFlowEntity) {
        executorService.execute { cashDao.insert(cash) }
    }

    fun getSumCash(status:String): Double {
        return cashDao.getSumCash(status).toDouble()
    }

    fun getDate(): List<String> {
        return cashDao.getDate()
    }

    fun getSumNominal(date:String, status:String): Double {
        return cashDao.getSumNominal(date, status)
    }
}