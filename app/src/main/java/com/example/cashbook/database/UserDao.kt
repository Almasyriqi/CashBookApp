package com.example.cashbook.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE user_name LIKE :userName")
    fun getUsername(userName: String): UserEntity?

    @Query("SELECT * FROM users WHERE password LIKE :password")
    fun getPassword(password: String): UserEntity?

    @Query("SELECT COUNT(userId) FROM users")
    fun getCount(): Int

    @Update
    fun updatePassword(user: UserEntity)
}