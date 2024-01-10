package com.example.githubrepoviewer.repos.data.repository

import com.example.githubrepoviewer.repos.data.dtos.ReposResponseItem
import com.example.githubrepoviewer.repos.data.local.ReposLocalSource
import com.example.githubrepoviewer.repos.data.mappers.toRepoEntity
import com.example.githubrepoviewer.repos.data.mappers.toRepoModel
import com.example.githubrepoviewer.repos.data.remote.ReposRemoteSource
import com.example.githubrepoviewer.repos.domain.models.RepoModel
import com.example.githubrepoviewer.repos.domain.repository.IAllReposRepository
import com.example.githubrepoviewer.utils.Resource
import retrofit2.Response
import javax.inject.Inject

class AllReposRepository @Inject constructor(
    private val reposRemoteSource: ReposRemoteSource,
    private val reposLocalSource: ReposLocalSource
) :
    IAllReposRepository {
    override suspend fun getAllRepos(): Resource<List<RepoModel>> {
        try {
            reposRemoteSource.getAllRepos().also { result ->
                updateLocalRepos(result)
            }
        } catch (e: Exception) {
            if (reposLocalSource.getAllRepos().isEmpty()) {
                return Resource.Failure("Something went wrong, reconnect and retry.")
            }
        }
        return Resource.Success(reposLocalSource.getAllRepos().map { it.toRepoModel() })
    }

    private suspend fun updateLocalRepos(result: Response<List<ReposResponseItem>>) {
        val repos = result.body() ?: listOf()
        if (result.isSuccessful) {
            reposLocalSource.upsertAll(repos.map { it.toRepoEntity() })
        }
    }
}