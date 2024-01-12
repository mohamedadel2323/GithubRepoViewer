package com.example.githubrepoviewer.repos.presentation

import androidx.paging.PagingData
import com.example.githubrepoviewer.repos.presentation.models.RepoUiModel

data class ReposScreenState(
    val repos: PagingData<RepoUiModel>? = null,
    val isReposLoading: Boolean = false,
    val errorMessage: String = ""
)