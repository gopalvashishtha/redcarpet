package com.example.redcarpet.network

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module

@InstallIn(ApplicationComponent::class)
object Networking {
    @Singleton

    @Provides
    fun provideHttpLoggerInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    @Singleton
    @Provides
    fun provideCallFactory(httpLoggingInterceptor: HttpLoggingInterceptor): Call.Factory{
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
    @Singleton
    @Provides
    fun provideMoshi() : Moshi{
        return Moshi.Builder().build()
    }
    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory{
        return MoshiConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRxJavaCallAdapterFactory(): RxJava3CallAdapterFactory{
        return RxJava3CallAdapterFactory.create()
    }
    @Singleton
    @Provides
    fun provideBaseUrl(): String{
        return "https://newsapi.org/v2/"
    }
    @Singleton
    @Provides
    fun provideRetrofit(httpLoggingInterceptor:Call.Factory,moshiConverterFactory: MoshiConverterFactory,rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,baseUrl:String):Retrofit{
        return Retrofit.Builder()
            .callFactory(httpLoggingInterceptor)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .baseUrl(baseUrl)
            .build()   }

    @Singleton
    @Provides
    fun provideNewsService(retrofit: Retrofit): APIRequest {
        return retrofit.create(APIRequest::class.java)
    }

}
