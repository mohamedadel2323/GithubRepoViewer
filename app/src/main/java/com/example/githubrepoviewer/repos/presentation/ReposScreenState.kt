package com.example.githubrepoviewer.repos.presentation

import com.example.githubrepoviewer.repos.presentation.models.RepoUiModel

data class ReposScreenState(
    val repos: List<RepoUiModel> = listOf(),
    val isReposLoading: Boolean = false,
    val repoIdLoading : Int = 0,
    val errorMessage: String = ""
)