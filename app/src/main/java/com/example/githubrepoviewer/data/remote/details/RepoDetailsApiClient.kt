package com.example.githubrepoviewer.data.remote.details

import com.example.githubrepoviewer.data.remote.GithubApiService
import javax.inject.Inject

class RepoDetailsApiClient @Inject constructor(private val githubApiService: GithubApiService): RepoDetailsRemoteSource {
    override suspend fun getRepoDetails(owner: String, repoName: String) =
        githubApiService.getRepoDetails(owner, repoName)
}