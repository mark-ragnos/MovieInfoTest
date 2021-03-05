package com.example.movieinfotest

import Results
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfotest.databinding.ActivityMainBinding
import com.example.movieinfotest.listadapter.MovieAdapter
import com.example.movieinfotest.listadapter.MoviePagingSource
import com.example.movieinfotest.network.MovieHelper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

//64561f5c70d6ee91504935b9f83a94a07455e910

class MainActivity : AppCompatActivity() {
    val TEST = "TEST"

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



    }

    fun testList(){
        val movieAdapter = MovieAdapter()

        binding.rvTest.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            adapter = movieAdapter
        }

        val helper = MovieHelper()

        val movies: Flow<PagingData<Results>> = Pager(PagingConfig(pageSize = 1)) {
            MoviePagingSource(helper)
        }.flow

        GlobalScope.launch {
            movies.collectLatest {
                movieAdapter.submitData(it)
            }
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


ВОПРОСЫ:
1 - Парсинг лишних данных
2 - OkHttp
 */