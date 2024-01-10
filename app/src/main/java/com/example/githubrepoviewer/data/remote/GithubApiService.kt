package com.example.githubrepoviewer.data.remote

import com.example.githubrepoviewer.repos.data.dtos.ReposResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface GithubApiService {
    companion object{
        const val REPOSITORIES = "repositories"
        const val OWNER = "owner"
        const val REPO = "repo"
        const val DETAILS = "repos/{$OWNER}/{$REPO}"
        const val ISSUES = "repos/{$OWNER}/{$REPO}/issues"
    }

    @GET(REPOSITORIES)
    suspend fun getRepositories(): Response<List<ReposResponseItem>>
}