package com.example.kotlin_pr5.model.todos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodosDao {
    @Query("select * from todos")
    fun getAllTodos(): LiveData<List<Todos>>

    @Insert
    fun insertTodos(vararg todos: Todos)

    @Query("select * from todos where id = :id ")
    fun getTodosById(id: Int): Todos

    @Update
    fun updateTodos(todos: Todos)

    @Delete
    fun deleteTodos(todos: Todos)
}