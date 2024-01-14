package com.example.githubrepoviewer.search.data.repository

import com.example.githubrepoviewer.repos.domain.models.RepoModel
import com.example.githubrepoviewer.search.domain.repository.ISearchRepository
import com.example.githubrepoviewer.utils.Resource

class FakeSearchRepository(private val repos: List<RepoModel>): ISearchRepository {

    private var shouldReturnError = false

    fun setReturnError(value: Boolean){
        shouldReturnError = value
    }

    override suspend fun searchForRepos(query: String): Resource<List<RepoModel>> {
        if (shouldReturnError){
            return Resource.Failure("Something went wrong, reconnect and retry.")
        }
        return Resource.Success(repos.filter { it.repoName.contains(query) || it.repoOwner.contains(query) })
    }
}