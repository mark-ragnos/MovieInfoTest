package com.example.movieinfotest.presentation.di.base

import android.util.Log
import com.example.movieinfotest.BuildConfig
import com.example.movieinfotest.data.api.ApiMovie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient): ApiMovie {
        val url = BuildConfig.API_URL

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiMovie::class.java)
    }

    @Provides
    fun provideClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)
        }
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }
}
