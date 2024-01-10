package com.example.githubrepoviewer.repos.data.local

import kotlinx.coroutines.flow.Flow


interface ReposLocalSource {
    suspend fun upsertAll(repos: List<RepoEntity>)
    fun getAllRepos(): Flow<List<RepoEntity>>
    fun updateRepo(id: Int, currentStarCount: Int)
}