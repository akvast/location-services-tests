package com.github.akvast.locations.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "locations")
class LocationUpdate {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @ColumnInfo(name = "latitude")
    var latitude: Double = 0.0

    @ColumnInfo(name = "longitude")
    var longitude: Double = 0.0

    @ColumnInfo(name = "date")
    var date = Date()

}