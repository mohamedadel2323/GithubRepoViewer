package com.example.githubrepoviewer.repos.domain.repository

import com.example.githubrepoviewer.repos.domain.models.RepoModel
import com.example.githubrepoviewer.utils.Resource

interface IAllReposRepository {
    suspend fun getAllRepos(): Resource<List<RepoModel>>
}