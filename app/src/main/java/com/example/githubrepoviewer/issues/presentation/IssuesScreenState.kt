package com.example.githubrepoviewer.issues.presentation

import com.example.githubrepoviewer.issues.presentation.models.IssueUiModel

data class IssuesScreenState(
    val issues: List<IssueUiModel> = listOf(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val networkCallCreated: Boolean = false
)
