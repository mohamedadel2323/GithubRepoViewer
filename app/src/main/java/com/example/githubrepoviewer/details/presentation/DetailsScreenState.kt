package com.example.githubrepoviewer.details.presentation

import com.example.githubrepoviewer.details.presentation.models.RepoDetailsUiModel

data class DetailsScreenState(
    val repo: RepoDetailsUiModel? = null,
    val isReposLoading: Boolean = false,
    val errorMessage: String = "",
    val networkCallCreated: Boolean = false
)