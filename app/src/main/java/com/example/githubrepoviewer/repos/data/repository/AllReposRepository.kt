package com.example.githubrepoviewer.repos.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.githubrepoviewer.data.remote.details.RepoDetailsRemoteSource
import com.example.githubrepoviewer.repos.data.dtos.repos.ReposResponseItem
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
import javax.inject.Inject

class AllReposRepository @Inject constructor(
    private val reposRemoteSource: ReposRemoteSource,
    private val reposDetailsRemoteSource: RepoDetailsRemoteSource,
    private val reposLocalSource: ReposLocalSource
) :
    IAllReposRepository {
    override suspend fun getAllRepos(): Resource<String> {
        try {
            reposRemoteSource.getAllRepos().also { result ->
                updateLocalRepos(result)
                return Resource.Success("Success")
            }
        } catch (e: Exception) {
            return Resource.Success("Success")
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
            return Resource.Failure("Something went wrong, reconnect and retry.")
        }
        return Resource.Success("")
    }

    override fun getReposPagingData(): Flow<PagingData<RepoModel>> {
        val pagingSourceFactory = {reposLocalSource.getAllRepos()}
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false, initialLoadSize = PAGE_SIZE),
            pagingSourceFactory =  pagingSourceFactory
        ).flow.map { it.map { repoEntity -> repoEntity.toRepoModel() } }
    }

    private suspend fun updateLocalRepos(result: Response<List<ReposResponseItem>>) {
        val repos = result.body() ?: listOf()
        if (result.isSuccessful) {
            reposLocalSource.upsertAll(repos.map { it.toRepoEntity() })
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}