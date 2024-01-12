package com.example.githubrepoviewer.issues.domain.models

data class IssueModel(
    val id: Int,
    val issueTitle: String,
    val author: String,
    val createdAt: String,
    val updatedAt: String,
    val closedAt: String,
    val state: String
)