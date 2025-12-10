package com.example.musicapp.di

import com.example.musicapp.data.remote.MusicApi
import com.example.musicapp.data.repository.MusicRepository
import com.example.musicapp.data.repository.MusicRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMusicApi(): MusicApi {
        return Retrofit.Builder()
            .baseUrl("https://music-api-u681.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MusicApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMusicRepository(api: MusicApi): MusicRepository {
        return MusicRepositoryImpl(api)
    }
}
