package com.asantherrera.savemoney365days.database

import android.content.Context
import androidx.room.*

@Database(
    entities = [Saving::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class SavingDB: RoomDatabase() {
    abstract fun savingDao():SavingDao

    companion object{
        @Volatile
        private var INSTANCE: SavingDB? = null
        fun getInstance(context: Context):SavingDB{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SavingDB::class.java,
                        "save-money-database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}