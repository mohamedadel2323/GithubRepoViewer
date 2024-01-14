package com.example.githubrepoviewer.details.data

import com.example.githubrepoviewer.details.domain.models.RepoDetailsModel
import com.example.githubrepoviewer.details.domain.repository.IRepositoryDetails
import com.example.githubrepoviewer.utils.Resource

class FakeDetailsRepository(private val repoDetailsList : List<RepoDetailsModel>): IRepositoryDetails {

    private var shouldReturnError = false

    fun setReturnError(value: Boolean){
        shouldReturnError = value
    }

    override suspend fun getRepoDetails(
        ownerName: String,
        repoName: String
    ): Resource<RepoDetailsModel> {
        if (shouldReturnError){
            return Resource.Failure("Something went wrong, reconnect and retry.")
        }
        return Resource.Success(repoDetailsList.first { (it.repoName == repoName && it.repoOwner == ownerName) })
    }
}