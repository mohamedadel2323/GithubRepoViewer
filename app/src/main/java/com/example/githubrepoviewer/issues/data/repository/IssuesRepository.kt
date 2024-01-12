package com.example.githubrepoviewer.issues.data.repository

import com.example.githubrepoviewer.issues.data.mappers.toIssueModel
import com.example.githubrepoviewer.issues.data.remote.IssuesRemoteSource
import com.example.githubrepoviewer.issues.domain.models.IssueModel
import com.example.githubrepoviewer.issues.domain.repository.IIssuesRepository
import com.example.githubrepoviewer.utils.Resource
import javax.inject.Inject

class IssuesRepository @Inject constructor(private val issuesRemoteSource: IssuesRemoteSource) :
    IIssuesRepository {
    override suspend fun getRepoIssues(
        ownerName: String,
        repoName: String
    ): Resource<List<IssueModel>> {
        try {
            issuesRemoteSource.getRepoIssues(ownerName, repoName).also { response ->
                return if (response.isSuccessful) {
                    Resource.Success(response.body()?.map { it.toIssueModel() }!!)
                } else {
                    Resource.Failure("Something went wrong, reconnect and retry.")
                }
            }
        } catch (e: Exception) {
            return Resource.Failure("${e.message}")
        }

    }
}