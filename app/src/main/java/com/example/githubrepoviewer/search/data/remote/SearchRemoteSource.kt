package com.example.githubrepoviewer.search.data.remote

import com.example.githubrepoviewer.search.data.dtos.SearchResponse
import retrofit2.Response

interface SearchRemoteSource {
    suspend fun searchForRepo(query: String): Response<SearchResponse>
}