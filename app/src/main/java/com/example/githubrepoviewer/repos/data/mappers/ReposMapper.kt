package com.example.githubrepoviewer.repos.data.mappers

import com.example.githubrepoviewer.details.data.dtos.RepoDetailsResponse
import com.example.githubrepoviewer.repos.data.dtos.repos.ReposResponseItem
import com.example.githubrepoviewer.repos.data.local.RepoEntity
import com.example.githubrepoviewer.repos.domain.models.RepoModel


fun ReposResponseItem.toRepoEntity(): RepoEntity =
    RepoEntity(
        id = this.id ?: 0,
        repoName = this.name ?: "",
        repoOwner = owner?.login ?: "",
        description = this.description ?: ""
    )

fun RepoEntity.toRepoModel(): RepoModel = RepoModel(
    id = this.id,
    repoName = this.repoName,
    repoOwner = repoOwner,
    description = this.description,
    starCount = starCount
)

fun RepoDetailsResponse.toRepoModel(): RepoModel = RepoModel(
    id = this.id ?: 0,
    repoName = this.name ?: "",
    repoOwner = owner?.login ?: "",
    description = this.description ?: "",
    starCount = stargazers_count?:0
)