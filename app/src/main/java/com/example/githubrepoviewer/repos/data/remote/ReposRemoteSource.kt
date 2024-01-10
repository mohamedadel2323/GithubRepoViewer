package com.example.githubrepoviewer.repos.data.remote

import com.example.githubrepoviewer.repos.data.dtos.ReposResponseItem
import retrofit2.Response

interface ReposRemoteSource {
    suspend fun getAllRepos(): Response<List<ReposResponseItem>>
}