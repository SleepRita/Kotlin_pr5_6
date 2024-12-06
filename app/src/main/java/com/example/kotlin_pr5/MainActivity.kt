package com.example.kotlin_pr5

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.kotlin_pr5.databinding.ActivityMainBinding
import com.example.kotlin_pr5.model.todos.Todos
import com.example.kotlin_pr5.model.todos.TodosDatabase
import com.example.kotlin_pr5.retrofit.api.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        vm = ViewModelProvider(this).get(DataViewModel::class.java)
        vm.todo.observe(this) {
            binding.todoText.text = vm.todo.value
        }
        vm.completed.observe(this) {
            binding.completedText.text = vm.completed.value
        }

        val retrofit = Retrofit.Builder().baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val todosMainApi = retrofit.create(MainApi::class.java)

        val db = Room.databaseBuilder(applicationContext, TodosDatabase::class.java, "todos_db")
            .build()
        val dao = db.TodosDao()

        binding.button.setOnClickListener() {
            val randId = Random.nextInt(1, 50)

            val result: Deferred<Todos> = CoroutineScope(Dispatchers.IO).async {
                try {
                    val todos = todosMainApi.getTodosById(randId)
                    dao.insertTodos(todos)
                }
                catch (e: SQLiteConstraintException)
                {
                }
                dao.getTodosById(randId)
            }

            CoroutineScope(Dispatchers.IO).launch {
                val str = result.await()
                runOnUiThread {
                    vm.updateTodos(str.todo, str.completed)
                }
            }
        }
    }
}