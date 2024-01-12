package com.example.githubrepoviewer.issues.data.remote

import com.example.githubrepoviewer.data.remote.GithubApiService
import javax.inject.Inject

class IssuesRemoteClient @Inject constructor(private val githubApiService: GithubApiService) :
    IssuesRemoteSource {
    override suspend fun getRepoIssues(ownerName: String, repoName: String) =
        githubApiService.getRepoIssues(ownerName, repoName)
}