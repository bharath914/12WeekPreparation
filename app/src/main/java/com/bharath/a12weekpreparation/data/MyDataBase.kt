package com.bharath.a12weekpreparation.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ProblemEntity::class
    ],
    version = 1
)
abstract class MyDataBase : RoomDatabase() {
    abstract val dao: ProblemsDao
}