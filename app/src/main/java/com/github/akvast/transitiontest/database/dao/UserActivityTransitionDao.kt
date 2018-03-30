package com.github.akvast.transitiontest.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.github.akvast.transitiontest.database.entities.UserActivityTransition
import io.reactivex.Flowable

@Dao
interface UserActivityTransitionDao {

    @Query("SELECT * from transitions ORDER BY date DESC")
    fun getAll(): Flowable<List<UserActivityTransition>>

    @Insert(onConflict = REPLACE)
    fun insert(transition: UserActivityTransition)

    @Query("DELETE from transitions")
    fun deleteAll()

}