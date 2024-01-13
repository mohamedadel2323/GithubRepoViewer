package com.example.githubrepoviewer.search.data.remote

import com.example.githubrepoviewer.data.remote.GithubApiService
import com.example.githubrepoviewer.search.data.dtos.SearchResponse
import retrofit2.Response
import javax.inject.Inject

class SearchRemoteClient @Inject constructor(private val githubApiService: GithubApiService) : SearchRemoteSource {
    override suspend fun searchForRepo(query: String): Response<SearchResponse> = githubApiService.searchForRepo(query)
}