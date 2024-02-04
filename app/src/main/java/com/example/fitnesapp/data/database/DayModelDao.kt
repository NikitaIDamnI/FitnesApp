package com.example.fitnesapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DayModelDao {
    @Query("SELECT * FROM days ")
    fun getDaysList(): LiveData<List<DayDbModel>>

    @Query("SELECT * FROM days WHERE dayNumber=:dayNumber LIMIT 1")
    suspend fun getDay(dayNumber: Int): DayDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDay(shopItemDbModel: DayDbModel)
}
