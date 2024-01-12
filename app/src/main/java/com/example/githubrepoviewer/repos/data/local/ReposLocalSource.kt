package com.example.githubrepoviewer.repos.data.local

import androidx.paging.PagingSource


interface ReposLocalSource {
    suspend fun upsertAll(repos: List<RepoEntity>)
    fun getAllRepos(): PagingSource<Int, RepoEntity>
    fun updateRepo(id: Int, currentStarCount: Int)
}