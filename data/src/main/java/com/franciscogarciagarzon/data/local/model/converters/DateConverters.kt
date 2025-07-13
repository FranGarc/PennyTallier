package com.franciscogarciagarzon.data.local.model.converters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateConverters { // Or class DateConverters
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    @JvmStatic // Important if methods are in an object or companion object
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    @JvmStatic // Important if methods are in an object or companion object
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let {
            return LocalDate.parse(it, formatter)
        }
    }
}