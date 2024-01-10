package com.example.githubrepoviewer.repos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RepoEntity::class], version = 1)
abstract class ReposDatabase : RoomDatabase() {
    abstract fun getRepoSDao(): ReposDao
}