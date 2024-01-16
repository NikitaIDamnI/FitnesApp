package com.example.fitnesapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DayModelDao {
    @Query("SELECT * FROM days ")
    suspend fun getDaysList(): LiveData<List<DayModelDb>>

    @Query("SELECT * FROM days WHERE dayNumber=:dayNumber LIMIT 1")
    suspend fun getDay(dayNumber: Int): LiveData<DayModelDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDay(shopItemDbModel: DayModelDb)
}
