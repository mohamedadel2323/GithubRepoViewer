package com.example.githubrepoviewer.repos.data.local


interface ReposLocalSource {
    suspend fun upsertAll(repos: List<RepoEntity>)
    fun getAllRepos(): List<RepoEntity>
}