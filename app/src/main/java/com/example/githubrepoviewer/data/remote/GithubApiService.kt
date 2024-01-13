package com.example.githubrepoviewer.data.remote

import com.example.githubrepoviewer.details.data.dtos.RepoDetailsResponse
import com.example.githubrepoviewer.issues.data.dtos.IssuesResponseItem
import com.example.githubrepoviewer.repos.data.dtos.repos.ReposResponseItem
import com.example.githubrepoviewer.search.data.dtos.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    companion object {
        const val REPOSITORIES = "repositories"
        const val OWNER = "owner"
        const val REPO = "repo"
        const val DETAILS = "repos/{$OWNER}/{$REPO}"
        const val ISSUES = "repos/{$OWNER}/{$REPO}/issues"
        const val SEARCH = "search/repositories"
        const val SEARCH_PARAMETER = "q"
    }

    @GET(REPOSITORIES)
    suspend fun getRepositories(): Response<List<ReposResponseItem>>

    @GET(DETAILS)
    suspend fun getRepoDetails(
        @Path(OWNER) ownerName: String,
        @Path(REPO) repoName: String
    ): Response<RepoDetailsResponse>

    @GET(ISSUES)
    suspend fun getRepoIssues(
        @Path(OWNER) ownerName: String,
        @Path(REPO) repoName: String
    ): Response<List<IssuesResponseItem>>

    @GET(SEARCH)
    suspend fun searchForRepo(@Query(SEARCH_PARAMETER) query: String): Response<SearchResponse>
}