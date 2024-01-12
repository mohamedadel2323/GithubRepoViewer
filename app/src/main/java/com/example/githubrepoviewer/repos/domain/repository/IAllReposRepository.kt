package com.example.githubrepoviewer.repos.domain.repository

import androidx.paging.PagingData
import com.example.githubrepoviewer.repos.domain.models.RepoModel
import com.example.githubrepoviewer.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IAllReposRepository {
    suspend fun getAllRepos(): Resource<String>

    suspend fun getRepoDetails(owner: String, repoName: String):Resource<String>

    fun getReposPagingData(): Flow<PagingData<RepoModel>>
}