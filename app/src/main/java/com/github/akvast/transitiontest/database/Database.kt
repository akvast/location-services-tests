package com.github.akvast.transitiontest.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.migration.Migration
import com.github.akvast.transitiontest.App
import com.github.akvast.transitiontest.database.converters.DateConverter
import com.github.akvast.transitiontest.database.dao.UserActivityDao
import com.github.akvast.transitiontest.database.dao.UserActivityTransitionDao
import com.github.akvast.transitiontest.database.entities.UserActivity
import com.github.akvast.transitiontest.database.entities.UserActivityTransition

object Database {

    private val database: AppDatabase by lazy { createDatabase() }

    // Dao getters:

    fun getUserActivityDao() = database.userActivityDao()

    fun getUserActivityTransitionDao() = database.userActivityTransitionDao()

    // Create:

    private fun createDatabase(): AppDatabase {
        return Room
                .databaseBuilder(App.context, AppDatabase::class.java, "app.db")
                .addMigrations(object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL(
                                "CREATE TABLE IF NOT EXISTS `transitions`" +
                                        " (`id` INTEGER PRIMARY KEY AUTOINCREMENT" +
                                        ", `activityType` INTEGER NOT NULL" +
                                        ", `transitionType` INTEGER NOT NULL" +
                                        ", `date` INTEGER NOT NULL)")
                    }
                })
                .build()
    }

    @Database(entities = [
        UserActivity::class,
        UserActivityTransition::class
    ], version = 2, exportSchema = false)
    @TypeConverters(DateConverter::class)
    abstract class AppDatabase : RoomDatabase() {

        abstract fun userActivityDao(): UserActivityDao

        abstract fun userActivityTransitionDao(): UserActivityTransitionDao

    }

}