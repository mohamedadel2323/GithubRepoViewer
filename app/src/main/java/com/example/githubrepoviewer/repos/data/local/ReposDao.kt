package com.example.githubrepoviewer.repos.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ReposDao {
    @Upsert
    suspend fun upsertAll(repos: List<RepoEntity>)
    @Query("SELECT * FROM repos_table")
    fun getAllRepos(): PagingSource<Int, RepoEntity>
    @Query("UPDATE repos_table SET starCount = :currentStarCount WHERE id = :id")
    fun updateRepo(id: Int, currentStarCount: Int)
}