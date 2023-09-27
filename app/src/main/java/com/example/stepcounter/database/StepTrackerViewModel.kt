package com.example.stepcounter.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.stepcounter.database.entities.Step
import kotlinx.coroutines.launch
import java.sql.Date

class StepTrackerViewModel(application: Application) : AndroidViewModel(application) {
    private val db = StepTrackerDB.getInstance(application)

    fun getAllSteps(): LiveData<List<Step>> {
        return db.stepDAO.getAllSteps()
    }

    fun getStepsByDate(date: Date): LiveData<Step> {
        return db.stepDAO.getStepsByDate(date)
    }

    fun addSteps(step: Step) {
        viewModelScope.launch {
            db.stepDAO.addSteps(step)
        }
    }
}