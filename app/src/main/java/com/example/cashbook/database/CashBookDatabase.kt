package com.example.cashbook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, CashFlowEntity::class], version = 1, exportSchema = true)
abstract class CashBookDatabase : RoomDatabase() {
    abstract val userDatabaseDao: UserDao
    abstract val cashFlowDatabaseDao : CashFlowDao

    companion object {
        @Volatile
        private var INSTANCE: CashBookDatabase? = null

        fun getInstance(context: Context): CashBookDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CashBookDatabase::class.java,
                        "cash_book_database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()

                    if(instance.userDatabaseDao.getCount() == 0){
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            CashBookDatabase::class.java,
                            "cash_book_database"
                        ).createFromAsset("CashBook.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                    }

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}