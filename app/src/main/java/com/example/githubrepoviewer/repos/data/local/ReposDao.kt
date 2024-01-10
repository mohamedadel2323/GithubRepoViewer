package com.example.githubrepoviewer.repos.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface ReposDao {
    @Upsert
    suspend fun upsertAll(repos: List<RepoEntity>)

    @Query("SELECT * FROM repos_table")
    fun getAllRepos(): List<RepoEntity>
}