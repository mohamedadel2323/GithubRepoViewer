package com.example.githubrepoviewer.repos.presentation.models

data class RepoUiModel(
    val id: Int,
    val repoName: String,
    val repoOwner: String,
    val description: String,
    val starCount: Int? = null,
)