package com.example.kotlin_pr5.retrofit.api

import retrofit2.http.GET
import retrofit2.http.Path
import com.example.kotlin_pr5.model.todos.Todos


interface MainApi {
    @GET("https://dummyjson.com/Todos/{id}")
    suspend fun getTodosById(@Path("id") id: Int): Todos
}