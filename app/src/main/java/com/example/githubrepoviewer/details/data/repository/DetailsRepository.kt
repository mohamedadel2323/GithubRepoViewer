package com.example.githubrepoviewer.details.data.repository

import com.example.githubrepoviewer.data.remote.details.RepoDetailsRemoteSource
import com.example.githubrepoviewer.details.data.mappers.toRepoDetailsModel
import com.example.githubrepoviewer.details.domain.models.RepoDetailsModel
import com.example.githubrepoviewer.details.domain.repository.IRepositoryDetails
import com.example.githubrepoviewer.utils.Resource
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val repoDetailsRemoteSource: RepoDetailsRemoteSource) :
    IRepositoryDetails {
    override suspend fun getRepoDetails(
        ownerName: String,
        repoName: String
    ): Resource<RepoDetailsModel> {
        try {
            repoDetailsRemoteSource.getRepoDetails(ownerName, repoName)
                .also { repoDetailsResponse ->
                    if (repoDetailsResponse.isSuccessful) {
                        return Resource.Success(
                            repoDetailsResponse.body()?.toRepoDetailsModel() ?: RepoDetailsModel(
                                0,
                                "",
                                "",
                                "",
                                "",
                                0,
                                0,
                                0,
                                0
                            )
                        )
                    } else {
                        return Resource.Failure("Something went wrong, reconnect and retry.")
                    }
                }

        } catch (e: Exception) {
            return Resource.Failure(e.message.toString())
        }
    }
}