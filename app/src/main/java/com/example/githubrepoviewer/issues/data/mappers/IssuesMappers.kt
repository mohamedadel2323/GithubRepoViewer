package com.example.githubrepoviewer.issues.data.mappers

import com.example.githubrepoviewer.issues.data.dtos.IssuesResponseItem
import com.example.githubrepoviewer.issues.domain.models.IssueModel

fun IssuesResponseItem.toIssueModel() = IssueModel(
    id = this.id ?: 0,
    issueTitle = title ?: "",
    author = user?.login ?: "",
    createdAt = created_at ?: "",
    updatedAt = updated_at ?: "",
    closedAt = closed_at ?: "",
    state = state ?: ""
)