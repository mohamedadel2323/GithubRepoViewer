package com.example.githubrepoviewer.data.remote.details

import com.example.githubrepoviewer.details.data.dtos.RepoDetailsResponse
import retrofit2.Response

interface RepoDetailsRemoteSource {
    suspend fun getRepoDetails(owner: String, repoName: String): Response<RepoDetailsResponse>
}