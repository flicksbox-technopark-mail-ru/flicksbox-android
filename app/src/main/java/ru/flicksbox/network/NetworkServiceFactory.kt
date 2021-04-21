package ru.flicksbox.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface NetworkServiceFactory {
    fun <T> createService(clazz: Class<T>): T
}

class NetworkServiceFactoryImpl : NetworkServiceFactory {
    companion object {
        const val API_ENDPOINT = "https://www.flicksbox.ru/api/v1/"
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(errorInterceptor)
            .addInterceptor(cookieCacherInterceptor)
            .addInterceptor(cookieSetterInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val gson: Gson by lazy {
        GsonBuilder().create()
    }

    private val converterFactory: Converter.Factory by lazy {
        GsonConverterFactory.create(gson)
    }

    private val cookieCacherInterceptor: CookieCacherInterceptor by lazy {
        CookieCacherInterceptor()
    }

    private val cookieSetterInterceptor: CookieSetterInterceptor by lazy {
        CookieSetterInterceptor()
    }

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val errorInterceptor: ResponseErrorInterceptor by lazy {
        ResponseErrorInterceptor(gson)
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().addConverterFactory(converterFactory).baseUrl(API_ENDPOINT)
            .client(okHttpClient).build()
    }

    override fun <T> createService(clazz: Class<T>): T =
        retrofit.create(clazz)
}