package com.example.githubrepoviewer.details.data.mappers

import com.example.githubrepoviewer.details.data.dtos.RepoDetailsResponse
import com.example.githubrepoviewer.details.domain.models.RepoDetailsModel

fun RepoDetailsResponse.toRepoDetailsModel(): RepoDetailsModel = RepoDetailsModel(
    id = this.id ?: 0,
    repoName = name ?: "",
    repoOwner = owner?.login ?: "",
    description = this.description ?: "",
    language = this.language?:"",
    starCount = stargazers_count?:0,
    repoWatchers = watchers_count?:0,
    forkCount = forks_count?:0,
    openIssuesCount = open_issues_count?:0
)