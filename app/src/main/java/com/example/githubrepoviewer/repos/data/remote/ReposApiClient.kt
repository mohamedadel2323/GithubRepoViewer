package com.example.githubrepoviewer.repos.data.remote

import com.example.githubrepoviewer.data.remote.GithubApiService
import com.example.githubrepoviewer.repos.data.dtos.ReposResponseItem
import retrofit2.Response
import javax.inject.Inject

class ReposApiClient @Inject constructor(private val githubApiService: GithubApiService) :
    ReposRemoteSource {
    override suspend fun getAllRepos(): Response<List<ReposResponseItem>> = githubApiService.getRepositories()
}