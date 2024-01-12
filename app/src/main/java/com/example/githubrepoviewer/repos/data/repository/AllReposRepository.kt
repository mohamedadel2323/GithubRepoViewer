package com.example.githubrepoviewer.repos.data.repository

import com.example.githubrepoviewer.data.remote.details.RepoDetailsRemoteSource
import com.example.githubrepoviewer.repos.data.dtos.ReposResponseItem
import com.example.githubrepoviewer.repos.data.local.ReposLocalSource
import com.example.githubrepoviewer.repos.data.mappers.toRepoEntity
import com.example.githubrepoviewer.repos.data.mappers.toRepoModel
import com.example.githubrepoviewer.repos.data.remote.ReposRemoteSource
import com.example.githubrepoviewer.repos.domain.models.RepoModel
import com.example.githubrepoviewer.repos.domain.repository.IAllReposRepository
import com.example.githubrepoviewer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class AllReposRepository @Inject constructor(
    private val reposRemoteSource: ReposRemoteSource,
    private val reposDetailsRemoteSource: RepoDetailsRemoteSource,
    private val reposLocalSource: ReposLocalSource
) :
    IAllReposRepository {
    override suspend fun getAllRepos(): Resource<Flow<List<RepoModel>>> {
        try {
            reposRemoteSource.getAllRepos().also { result ->
                updateLocalRepos(result)
                return Resource.Success(reposLocalSource.getAllRepos().map { repoList -> repoList.map { it.toRepoModel() } })
            }
        } catch (e: Exception) {
            return Resource.Success(reposLocalSource.getAllRepos().map { repoList -> repoList.map { it.toRepoModel() } })
        }

    }

    override suspend fun getRepoDetails(owner: String, repoName: String): Resource<String> {
        try {
            reposDetailsRemoteSource.getRepoDetails(owner, repoName).also { repoResponse ->
                val repo = repoResponse.body()?.toRepoModel() ?: RepoModel(0, "", "", "", 0)
                if (repoResponse.isSuccessful) {
                    reposLocalSource.updateRepo(repo.id, repo.starCount ?: 0)
                }
            }
        } catch (e: Exception) {
            Timber.e(e.message)
            return Resource.Failure("Something went wrong, reconnect and retry.")
        }
        return Resource.Success("")
    }

    private suspend fun updateLocalRepos(result: Response<List<ReposResponseItem>>) {
        val repos = result.body() ?: listOf()
        if (result.isSuccessful) {
            reposLocalSource.upsertAll(repos.map { it.toRepoEntity() })
        }
    }
}