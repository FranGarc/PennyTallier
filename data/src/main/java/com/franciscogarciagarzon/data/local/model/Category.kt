package com.franciscogarciagarzon.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val color: String = CategoryColor.BLUE_LIGHT.hexCode
)

enum class CategoryColor(val hexCode: String) {
    RED_LIGHT("0xFFFAE0E0"),
    ORANGE_LIGHT("0xFFFFF0D0"),
    YELLOW_LIGHT("0xFFFFFAD6"),
    GREEN_LIGHT("0xFFE0FAE0"),
    BLUE_LIGHT("0xFFE0F0FA"),
    PURPLE_LIGHT("0xFFEDDEFB"),
    CORAL_LIGHT("0xFFF8DCDC"),
    PALE_GREEN("0xFFDFF8DF"),
    LIGHT_STEEL_BLUE("0xFFDCEAF8"),
    WHITE("0xFFFFFFFF"),
    LIGHT_GRAY("0xFFF0F0F0"),
    IVORY("0xFFFFFFF0")
}
