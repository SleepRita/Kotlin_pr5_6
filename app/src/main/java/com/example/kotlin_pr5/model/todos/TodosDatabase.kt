package com.example.kotlin_pr5.model.todos

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Todos::class], version = 1)
abstract class TodosDatabase : RoomDatabase() {
    abstract fun TodosDao(): TodosDao
}