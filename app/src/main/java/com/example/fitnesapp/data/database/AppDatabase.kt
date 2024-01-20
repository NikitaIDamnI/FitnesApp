package com.example.fitnesapp.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DayDbModel::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun DayModelDao(): DayModelDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val NAME = "fitnes_app.db"


        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }

                val database = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    NAME
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = database
                return database
            }


        }
    }

}