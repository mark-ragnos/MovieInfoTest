package com.example.movieinfotest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.movieinfotest.database.MovieViewModel
import com.example.movieinfotest.databinding.ActivityMainBinding
import com.example.movieinfotest.network.MovieHelper
import kotlinx.coroutines.*
//64561f5c70d6ee91504935b9f83a94a07455e910

class MainActivity : AppCompatActivity() {
    val TEST = "TEST"

    lateinit var binding: ActivityMainBinding
    lateinit var viewModes: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        lifecycleScope.launch{

        }

    }

    fun print(message: String) {
        Log.d(TEST, message)
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


ВОПРОСЫ:
1 - Парсинг лишних данных
2 - OkHttp
 */