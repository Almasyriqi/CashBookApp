package com.example.cashbook.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CashFlowDao {
    @Query("SELECT * FROM cash_flow order by id desc")
    fun getAllCashFlow(): LiveData<List<CashFlowEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cash_flow: CashFlowEntity)

    @Query("select sum(nominal) from cash_flow where status = :status")
    fun getSumCash(status: String): Double

    @Query("select distinct(date) from cash_flow")
    fun getDate(): List<String>

    @Query("select sum(nominal) from cash_flow where date = :date and status = :status")
    fun getSumNominal(date:String, status:String): Double
}