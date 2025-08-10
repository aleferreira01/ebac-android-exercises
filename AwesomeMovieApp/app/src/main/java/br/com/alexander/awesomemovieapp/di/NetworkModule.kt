package br.com.alexander.awesomemovieapp.di

import br.com.alexander.awesomemovieapp.api.MovieService
import br.com.alexander.awesomemovieapp.data.ApiCredentials
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(ApiCredentials().baseUrl)
        .client(ApiCredentials().okHttpClient().build())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

}