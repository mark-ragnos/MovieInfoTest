package com.example.movieinfotest.presentation.di.modules

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.movieinfotest.BuildConfig
import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.api.ApiMovie
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.db.MovieDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(
    private val application: Application
) {

    @Provides
    fun getDbHelper(db: MovieDatabase): DbHelper {
        return DbHelper(db)
    }

    @Provides
    fun getDatabase(context: Context, dbName: String): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, dbName).build()
    }

    @Provides
    fun getApiHelper(api: ApiMovie): ApiHelper {
        return ApiHelper(api)
    }

    @Provides
    fun getApi(client: OkHttpClient): ApiMovie {
        val url = BuildConfig.API_URL

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiMovie::class.java)
    }

    @Provides
    fun getClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @Provides
    fun getLogger(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)
        }
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }

    @Provides
    fun getContext(): Context {
        return application
    }

    @Provides
    fun getDbName(): String {
        return BuildConfig.DB_NAME
    }
}
