package com.github.akvast.transitiontest.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.github.akvast.transitiontest.database.entities.UserActivity

@Dao
interface UserActivityDao {

    @Query("SELECT * from activities ORDER BY date DESC")
    fun getAll(): List<UserActivity>

    @Insert(onConflict = REPLACE)
    fun insert(activity: UserActivity)

    @Query("DELETE from activities")
    fun deleteAll()

}