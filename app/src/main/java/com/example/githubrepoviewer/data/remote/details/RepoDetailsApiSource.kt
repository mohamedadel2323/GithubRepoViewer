package com.example.githubrepoviewer.data.remote.details

interface RepoDetailsApiSource {
    suspend fun getRepoDetails(owner: String, repoName: String)
}