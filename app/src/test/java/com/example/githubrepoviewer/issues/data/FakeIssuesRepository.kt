package com.example.githubrepoviewer.issues.data

import com.example.githubrepoviewer.issues.domain.models.IssueModel
import com.example.githubrepoviewer.issues.domain.repository.IIssuesRepository
import com.example.githubrepoviewer.utils.Resource

class FakeIssuesRepository(private val issues: List<IssueModel>) : IIssuesRepository {

    private var shouldReturnError = false

    fun setReturnError(value: Boolean){
        shouldReturnError = value
    }

    override suspend fun getRepoIssues(
        ownerName: String,
        repoName: String
    ): Resource<List<IssueModel>> {
        if (shouldReturnError){
            return Resource.Failure("Something went wrong, reconnect and retry.")
        }
        return Resource.Success(issues)
    }
}