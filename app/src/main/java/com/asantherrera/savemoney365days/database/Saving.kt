package com.asantherrera.savemoney365days.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Saving(

    @PrimaryKey
    val id: Int,
    val date: Date,
    val currency: Currency
)