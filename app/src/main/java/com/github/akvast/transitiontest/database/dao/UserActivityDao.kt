package com.github.akvast.transitiontest.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.github.akvast.transitiontest.database.entities.UserActivity
import io.reactivex.Flowable

@Dao
interface UserActivityDao {

    @Query("SELECT * from activities ORDER BY date DESC")
    fun getAll(): Flowable<List<UserActivity>>

    @Insert(onConflict = REPLACE)
    fun insert(activity: UserActivity)

    @Query("DELETE from activities")
    fun deleteAll()

}