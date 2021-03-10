package com.example.movieinfotest


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.movieinfotest.database.MovieDatabase
import com.example.movieinfotest.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

//64561f5c70d6ee91504935b9f83a94a07455e910

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navController = findNavController(R.id.fragment)
        //test()
        binding.bottomNavigation.setupWithNavController(navController)
    }

    fun test() {
        val db = MovieApp.getInstance().getDatabase()
        val repository = Repository.create()

        GlobalScope.launch {
            db.genreDao().saveAll(repository.getAllGenres())

            Log.d("TEST", "All genres added")

            val res = db.genreDao().loadAll()

            res.forEach {
                Log.d("TEST", "ID: ${it.id}, NAME: ${it.name}")
            }
        }
    }

    companion object{
        fun makeRepository(){
            Repository.create()
        }
    }

}

/*
Требуемые макеты:
1. Макет инфы о фильме
- Содержит: постер, название, дата выхода, рейтинг, жанр, описание, список актеров

2. Макет генерации случайного фильма
- Содержит: 2 поля ввода (год и жанр), кнопка для генерации. Выводит: постер, название, рейтинг

3. Макет популярных фильмов
- Содержит: список, состоящий из постер, название, рейтинг, год

4. Список сохраненных фильмов (как у популярных + возможность сохранения)


 */