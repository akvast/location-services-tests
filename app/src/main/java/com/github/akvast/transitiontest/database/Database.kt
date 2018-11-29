package com.github.akvast.transitiontest.database

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.github.akvast.transitiontest.App
import com.github.akvast.transitiontest.database.converters.DateConverter
import com.github.akvast.transitiontest.database.dao.LocationUpdateDao
import com.github.akvast.transitiontest.database.dao.UserActivityDao
import com.github.akvast.transitiontest.database.dao.UserActivityTransitionDao
import com.github.akvast.transitiontest.database.entities.LocationUpdate
import com.github.akvast.transitiontest.database.entities.UserActivity
import com.github.akvast.transitiontest.database.entities.UserActivityTransition

object Database {

    private val database: AppDatabase by lazy { createDatabase() }

    // Dao getters:

    fun getUserActivityDao() = database.userActivityDao()

    fun getUserActivityTransitionDao() = database.userActivityTransitionDao()

    fun getLocationUpdateDao() = database.locationUpdateDao()

    @Database(entities = [
        UserActivity::class,
        UserActivityTransition::class,
        LocationUpdate::class
    ], version = 3, exportSchema = false)
    @TypeConverters(DateConverter::class)
    abstract class AppDatabase : RoomDatabase() {

        abstract fun userActivityDao(): UserActivityDao

        abstract fun userActivityTransitionDao(): UserActivityTransitionDao

        abstract fun locationUpdateDao(): LocationUpdateDao

    }

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
                .addMigrations(object: Migration(2, 3) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("CREATE TABLE IF NOT EXISTS `locations`" +
                                " (`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                                " `latitude` REAL NOT NULL," +
                                " `longitude` REAL NOT NULL," +
                                " `date` INTEGER NOT NULL)")
                    }
                })
                .build()
    }

}