package com.example.githubrepoviewer.di

import android.content.Context
import com.example.githubrepoviewer.BuildConfig
import com.example.githubrepoviewer.data.remote.ApiServiceManager
import com.example.githubrepoviewer.data.remote.GithubApiService
import com.example.githubrepoviewer.data.remote.details.RepoDetailsApiClient
import com.example.githubrepoviewer.data.remote.details.RepoDetailsRemoteSource
import com.example.githubrepoviewer.utils.NetworkConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModule {
    @Binds
    @Singleton
    abstract fun bindReposDetailsRemoteSource(reposDetailsApiClient: RepoDetailsApiClient): RepoDetailsRemoteSource

    companion object{
        @Provides
        @Singleton
        fun provideLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

        @Provides
        @Singleton
        fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
            OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

        @Provides
        @Singleton
        fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder =
            Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())

        @Provides
        @Singleton
        fun provideGithubApi(builder: Retrofit.Builder): GithubApiService =
            ApiServiceManager(builder).provideService(
                service = GithubApiService::class.java,
                baseUrl = BuildConfig.GIT_HUB_BASE_URL
            )

        @Provides
        @Singleton
        fun provideConnectivityObserver(@ApplicationContext context: Context): NetworkConnectivityObserver =
            NetworkConnectivityObserver(context = context)
    }

}