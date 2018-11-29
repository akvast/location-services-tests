package com.github.akvast.locations.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.github.akvast.locations.database.entities.UserActivityTransition

@Dao
interface UserActivityTransitionDao {

    @Query("SELECT * from transitions ORDER BY date DESC")
    fun getAll(): List<UserActivityTransition>

    @Insert(onConflict = REPLACE)
    fun insert(transition: UserActivityTransition)

    @Query("DELETE from transitions")
    fun deleteAll()

}