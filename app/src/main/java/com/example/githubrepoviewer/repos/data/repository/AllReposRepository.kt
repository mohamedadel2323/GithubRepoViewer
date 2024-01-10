package com.example.githubrepoviewer.repos.data.repository

import com.example.githubrepoviewer.repos.data.mappers.toRepoModel
import com.example.githubrepoviewer.repos.data.remote.ReposRemoteSource
import com.example.githubrepoviewer.repos.domain.models.RepoModel
import com.example.githubrepoviewer.repos.domain.repository.IAllReposRepository
import com.example.githubrepoviewer.utils.Resource
import javax.inject.Inject

class AllReposRepository @Inject constructor(private val reposRemoteSource: ReposRemoteSource) :
    IAllReposRepository {
    override suspend fun getAllRepos(): Resource<List<RepoModel>> {
        try {
            reposRemoteSource.getAllRepos().also {result->
                return if (result.isSuccessful) {
                    Resource.Success(result.body()?.map { it.toRepoModel() }?: listOf())
                }else{
                    Resource.Failure(result.message())
                }
            }
        }catch (e:Exception){
             return Resource.Failure(e.message.toString())
        }

    }
}