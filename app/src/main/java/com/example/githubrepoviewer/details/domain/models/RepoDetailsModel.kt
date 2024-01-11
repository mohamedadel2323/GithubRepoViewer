package com.example.githubrepoviewer.details.domain.models

data class RepoDetailsModel(
    val id: Int,
    val repoName: String,
    val repoOwner: String,
    val description: String,
    val language: String,
    val starCount: Int,
    val repoWatchers: Int,
    val forkCount: Int,
    val openIssuesCount: Int
)
