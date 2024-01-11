package com.example.githubrepoviewer.details.domain.repository

import com.example.githubrepoviewer.details.domain.models.RepoDetailsModel
import com.example.githubrepoviewer.utils.Resource

interface IRepositoryDetails {
    suspend fun getRepoDetails(ownerName: String, repoName: String): Resource<RepoDetailsModel>
}