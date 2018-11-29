package com.github.akvast.transitiontest.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.util.*


@Entity(tableName = "transitions")
class UserActivityTransition {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @ColumnInfo(name = "activityType")
    var activityType: Int = 0

    @ColumnInfo(name = "transitionType")
    var transitionType: Int = 0

    @ColumnInfo(name = "date")
    var date = Date()

}