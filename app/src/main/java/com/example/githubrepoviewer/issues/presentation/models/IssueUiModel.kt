package com.example.githubrepoviewer.issues.presentation.models

data class IssueUiModel(
    val id: Int,
    val issueTitle: String,
    val author: String,
    val createdAt: String,
    val updatedAt: String,
    val closedAt: String,
    val state: String
)