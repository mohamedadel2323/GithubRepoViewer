package com.example.githubrepoviewer.repos.presentation

import com.example.githubrepoviewer.repos.presentation.models.RepoUiModel

data class ReposScreenState(
    val repos: List<RepoUiModel> = listOf(),
    val isReposLoading: Boolean = false,
    val repoLoading: RepoLoading = RepoLoading(0, false),
    val errorMessage: String = ""
)

data class RepoLoading(val id: Int, val isLoading: Boolean)
