package com.example.githubrepoviewer.repos.presentation.mappers

import com.example.githubrepoviewer.repos.domain.models.RepoModel
import com.example.githubrepoviewer.repos.presentation.models.RepoUiModel

fun RepoModel.toRepoUiModel(): RepoUiModel =
    RepoUiModel(
        id = this.id,
        repoName = this.repoName.replaceEmpty(),
        repoOwner = repoOwner.replaceEmpty(),
        description = this.description.replaceEmpty(),
        starCount = this.starCount
    )

private fun String.replaceEmpty(): String = this.ifEmpty { "Not Available" }