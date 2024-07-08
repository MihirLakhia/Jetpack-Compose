package com.generic.circleofcare.daggerhiltexample.di

import com.generic.circleofcare.daggerhiltexample.MyApp
import com.generic.circleofcare.daggerhiltexample.data.remote.MyApi
import com.generic.circleofcare.daggerhiltexample.data.repository.MyRepositoryImpl
import com.generic.circleofcare.daggerhiltexample.domain.repository.MyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //life time in Application  other : Activity, ViewModel, Retain, ServiceComponent
object AppModule {
    @Provides
    @Singleton // Scope
    fun provideMyApi():MyApi{
        return Retrofit.Builder()
            .baseUrl("https://test.com")
            .build()
            .create(MyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMyRepository(api: MyApi, app: MyApp) :MyRepository{
        return MyRepositoryImpl(api, app)
    }
    @Provides
    @Singleton
    fun provideString1() = "Hello 1"
    @Provides
    @Singleton
    fun provideString2() = "Hello 2"
}