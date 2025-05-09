package br.com.alexander.awesomemovieapp

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

class ApiCredentials {
    val baseUrl = "https://api.themoviedb.org/3/"
    val apiKey = ""

    fun okHttpClient() = OkHttpClient().newBuilder()
        .addInterceptor(
            Interceptor { chain ->
                val request: Request = chain.request()
                    .newBuilder()
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer $apiKey")
                    .build()
                chain.proceed(request)
            }
        )
}