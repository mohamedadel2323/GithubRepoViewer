package com.example.githubrepoviewer.issues.data.remote

import com.example.githubrepoviewer.issues.data.dtos.IssuesResponseItem
import retrofit2.Response


interface IssuesRemoteSource {
    suspend fun getRepoIssues(ownerName: String, repoName: String): Response<List<IssuesResponseItem>>
}