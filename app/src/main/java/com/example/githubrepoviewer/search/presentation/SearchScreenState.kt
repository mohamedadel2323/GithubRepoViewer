package com.example.githubrepoviewer.search.presentation

import com.example.githubrepoviewer.repos.presentation.models.RepoUiModel

data class SearchScreenState(
    val searchRepos: List<RepoUiModel>? = null,
    val isReposLoading: Boolean = false,
    val errorMessage: String = ""
)
