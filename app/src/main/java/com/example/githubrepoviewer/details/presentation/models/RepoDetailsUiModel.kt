package com.example.githubrepoviewer.details.presentation.models

data class RepoDetailsUiModel(
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
