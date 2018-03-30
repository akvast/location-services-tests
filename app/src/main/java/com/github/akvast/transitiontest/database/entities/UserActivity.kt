package com.github.akvast.transitiontest.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo
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