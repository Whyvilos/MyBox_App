package com.example.mybox.di

import com.example.mybox.data.remote.MyBoxApi
import com.example.mybox.repository.MyBoxRepository
import com.example.mybox.util.Constants.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun myBoxRepository(
        api: MyBoxApi
    ) = MyBoxRepository(api)
    @Singleton
    @Provides
    fun provideMyBoxApi(): MyBoxApi {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()
            .create(MyBoxApi::class.java)
    }
}