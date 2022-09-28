package com.example.cashbook

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cashbook.database.UserDao
import com.example.cashbook.database.UserEntity

class CashBookRepository(private val dao: UserDao) {
    val users = dao.getAllUsers()
    suspend fun getUserName(userName: String):UserEntity?{
        return dao.getUsername(userName)
    }
}