package com.example.githubrepoviewer.search.domain.repository

import com.example.githubrepoviewer.repos.domain.models.RepoModel
import com.example.githubrepoviewer.utils.Resource

interface ISearchRepository {
    suspend fun searchForRepos(query:String): Resource<List<RepoModel>>
}