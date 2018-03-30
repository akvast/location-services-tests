package com.github.akvast.transitiontest.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo
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