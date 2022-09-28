package com.example.cashbook

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cashbook.database.CashBookDatabase
import com.example.cashbook.database.UserDao
import com.example.cashbook.database.UserEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CashBookRepository(private val dao: UserDao) {
    val users = dao.getAllUsers()

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
}