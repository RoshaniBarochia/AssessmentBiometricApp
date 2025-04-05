package com.app.assessment.network

import com.app.assessment.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(getOkHttpClientBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Http builder
    private fun getOkHttpClientBuilder(): OkHttpClient.Builder {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
        val requestInterceptor = Interceptor { chain ->
            val request = chain.request()
            println("Request: ${request.method} ${request.url}") // ✅ Correct way to access method & URL
            val response = chain.proceed(request)
            println("Response: ${response.code}") // ✅ Correct way to access status code
            response
        }
        val oktHttpClientBuilder = OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor) // Add logging interceptor
            .addInterceptor(requestInterceptor)

        return oktHttpClientBuilder
    }



    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

