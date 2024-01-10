package com.example.githubrepoviewer.repos.domain.repository

import com.example.githubrepoviewer.repos.domain.models.RepoModel
import com.example.githubrepoviewer.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IAllReposRepository {
    suspend fun getAllRepos(): Resource<Flow<List<RepoModel>>>

    suspend fun getRepoDetails(owner: String, repoName: String):Resource<String>
}