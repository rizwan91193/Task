package com.justclean.mytask.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.justclean.mytask.data.PostApi
import com.justclean.mytask.data.db.AppDatabase
import com.justclean.mytask.data.network.NetworkConnectionInterceptor
import com.justclean.mytask.data.repo.PostRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.annotation.PostConstruct
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    const val BASE_URL = "https://jsonplaceholder.typicode.com"


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create(gson)
        )
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun providesPostApi(retrofit: Retrofit): PostApi = retrofit.create(PostApi::class.java)

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext applicationContext: Context) =
        AppDatabase.invoke(applicationContext)

    @Singleton
    @Provides
    fun providePostDao(db: AppDatabase) = db.getPostDao()

    @Singleton
    @Provides
    fun provideRepository(
        postapi: PostApi,
        db: AppDatabase
    ) =
        PostRepo(postapi, db)

}