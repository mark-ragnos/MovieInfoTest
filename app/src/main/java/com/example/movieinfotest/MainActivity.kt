package com.example.movieinfotest


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movieinfotest.databinding.ActivityMainBinding
import com.example.movieinfotest.repositories.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//64561f5c70d6ee91504935b9f83a94a07455e910

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.fragment)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_menu, menu)

        if(viewModel.getDarkMode())
            menu?.getItem(0)?.icon = getDrawable(R.drawable.ic_light_mode)
        else{
            menu?.getItem(0)?.icon = getDrawable(R.drawable.ic_dark_mode)
        }

        return super.onCreateOptionsMenu(menu)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.dark_mode_btn->{
                if(!viewModel.getDarkMode()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                viewModel.changeMode()
            }
        }


        return super.onOptionsItemSelected(item)
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