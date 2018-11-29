package com.github.akvast.locations.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.akvast.locations.database.entities.LocationUpdate

@Dao
interface LocationUpdateDao {

    @Query("SELECT * from locations ORDER BY date DESC")
    fun getAll(): List<LocationUpdate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locationUpdate: LocationUpdate)

    @Query("DELETE from locations")
    fun deleteAll()

}