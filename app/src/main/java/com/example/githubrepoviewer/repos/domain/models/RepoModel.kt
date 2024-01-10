package com.example.githubrepoviewer.repos.domain.models

data class RepoModel(
    val id: Int,
    val repoName: String,
    val repoOwner: String,
    val description: String,
    val starCount: Int? = null,
)
