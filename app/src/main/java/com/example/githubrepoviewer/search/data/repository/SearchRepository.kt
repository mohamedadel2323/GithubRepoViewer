package com.example.githubrepoviewer.search.data.repository

import com.example.githubrepoviewer.repos.data.mappers.toRepoModel
import com.example.githubrepoviewer.repos.domain.models.RepoModel
import com.example.githubrepoviewer.search.data.remote.SearchRemoteSource
import com.example.githubrepoviewer.search.domain.repository.ISearchRepository
import com.example.githubrepoviewer.utils.Resource
import javax.inject.Inject

class SearchRepository @Inject constructor(private val searchRemoteSource: SearchRemoteSource) : ISearchRepository {
    override suspend fun searchForRepos(query: String): Resource<List<RepoModel>> {
        try {
            searchRemoteSource.searchForRepo(query).also { searchResponse->
                val repos = searchResponse.body()?.items?.map { it.toRepoModel() }?: listOf()
                return if (searchResponse.isSuccessful){
                    Resource.Success(repos)
                }else {
                    Resource.Failure("Something went wrong, reconnect and retry.")
                }
            }
        }catch (e:Exception){
            return Resource.Failure(e.message.toString())
        }
    }
}