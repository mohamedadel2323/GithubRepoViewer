package com.example.githubrepoviewer.details.presentation.mappers

import com.example.githubrepoviewer.details.domain.models.RepoDetailsModel
import com.example.githubrepoviewer.details.presentation.models.RepoDetailsUiModel

fun RepoDetailsModel.toRepoDetailsUiModel() = RepoDetailsUiModel(
    id = this.id,
    repoName = this.repoName,
    repoOwner = this.repoOwner,
    description = this.description,
    language = this.language,
    starCount = this.starCount,
    repoWatchers = this.repoWatchers,
    forkCount = this.forkCount,
    openIssuesCount = this.openIssuesCount
)