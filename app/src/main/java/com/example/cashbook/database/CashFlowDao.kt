package com.example.cashbook.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CashFlowDao {
    @Query("SELECT * FROM cash_flow")
    fun getAllCashFlow(): LiveData<List<CashFlowEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cash_flow: CashFlowEntity)
}