package com.example.stepcounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stepcounter.database.entities.Step
import java.sql.Date

@Dao
interface StepDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addSteps(step: Step)

    @Query("SELECT * FROM step WHERE date = :date")
    fun getStepsByDate(date: Date): LiveData<Step>

    @Query("SELECT * FROM step")
    fun getAllSteps(): LiveData<List<Step>>

    @Query("SELECT * FROM step where date = :date")
    fun getStepSevenDays(date: Date): LiveData<Step>
}