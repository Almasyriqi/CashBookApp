package com.example.cashbook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cash_flow")
data class CashFlowEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "nominal")
    var nominal: Int? = 0,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "status")
    var status: String? = "pemasukan"
)