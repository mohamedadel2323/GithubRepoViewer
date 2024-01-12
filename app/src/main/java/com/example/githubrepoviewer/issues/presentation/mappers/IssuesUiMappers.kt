package com.example.githubrepoviewer.issues.presentation.mappers

import com.example.githubrepoviewer.issues.domain.models.IssueModel
import com.example.githubrepoviewer.issues.presentation.models.IssueUiModel

fun IssueModel.toIssueUiModel() = IssueUiModel(
    id = this.id,
    issueTitle = this.issueTitle,
    author = this.author,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    closedAt = this.closedAt,
    state = this.state
)