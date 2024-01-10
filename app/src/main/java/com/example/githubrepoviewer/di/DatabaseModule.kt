package com.example.githubrepoviewer.di

import android.content.Context
import androidx.room.Room
import com.example.githubrepoviewer.repos.data.local.ReposDao
import com.example.githubrepoviewer.repos.data.local.ReposDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideReposDatabase(@ApplicationContext context: Context): ReposDatabase =
        Room.databaseBuilder(context, ReposDatabase::class.java, "ReposDatabase").build()

    @Provides
    @Singleton
    fun provideReposDao(reposDatabase: ReposDatabase): ReposDao = reposDatabase.getRepoSDao()
}