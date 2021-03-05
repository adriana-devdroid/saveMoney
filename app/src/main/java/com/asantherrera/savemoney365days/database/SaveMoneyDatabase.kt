package com.asantherrera.savemoney365days.database

import android.app.Application
import android.content.Context
import androidx.room.DatabaseConfiguration
import androidx.room.Room

class SaveMoneyDatabase {
    fun getRoom(context: Context): SavingDB {
        return SavingDB.getInstance(context)
    }
}