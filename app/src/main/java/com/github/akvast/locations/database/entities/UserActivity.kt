package com.github.akvast.locations.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.util.*


@Entity(tableName = "activities")
class UserActivity {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @ColumnInfo(name = "type")
    var type: Int = 0

    @ColumnInfo(name = "confidence")
    var confidence: Int = 0

    @ColumnInfo(name = "date")
    var date = Date()

}