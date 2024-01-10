package com.example.githubrepoviewer.repos.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ReposDao {
    @Upsert
    suspend fun upsertAll(repos: List<RepoEntity>)
    @Query("SELECT * FROM repos_table")
    fun getAllRepos(): Flow<List<RepoEntity>>
    @Query("UPDATE repos_table SET starCount = :currentStarCount WHERE id = :id")
    fun updateRepo(id: Int, currentStarCount: Int)
}