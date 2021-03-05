package com.asantherrera.savemoney365days.database

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromString(currencyCode: String): Currency =
        Currency.getInstance(currencyCode)

    @TypeConverter
    fun currencyToString(currency: Currency): String =
        currency.currencyCode
}