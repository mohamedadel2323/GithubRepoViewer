package com.example.githubrepoviewer.issues.domain.repository

import com.example.githubrepoviewer.issues.domain.models.IssueModel
import com.example.githubrepoviewer.utils.Resource

interface IIssuesRepository {
    suspend fun getRepoIssues(ownerName: String, repoName: String): Resource<List<IssueModel>>
}