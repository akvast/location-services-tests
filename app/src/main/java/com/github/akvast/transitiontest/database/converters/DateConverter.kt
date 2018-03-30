package com.github.akvast.transitiontest.database.converters

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(value: Long?) = when (value) {
        null -> null
        else -> Date(value)
    }

    @TypeConverter
    fun toLong(date: Date?) = date?.time

}