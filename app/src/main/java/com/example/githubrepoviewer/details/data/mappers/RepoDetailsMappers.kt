package com.example.githubrepoviewer.details.data.mappers

import com.example.githubrepoviewer.details.data.dtos.RepoDetailsResponse
import com.example.githubrepoviewer.repos.domain.models.RepoModel

fun RepoDetailsResponse.toRepoModel(): RepoModel = RepoModel(
    id = this.id ?: 0,
    repoName = this.name ?: "",
    repoOwner = owner?.login ?: "",
    description = this.description ?: "",
    starCount = stargazers_count?:0
)