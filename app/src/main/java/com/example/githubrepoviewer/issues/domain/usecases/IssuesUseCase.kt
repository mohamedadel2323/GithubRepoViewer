package com.example.githubrepoviewer.issues.domain.usecases

import com.example.githubrepoviewer.issues.domain.repository.IIssuesRepository
import javax.inject.Inject

class IssuesUseCase @Inject constructor(private val iIssuesRepository: IIssuesRepository) {
    suspend fun getRepoIssues(ownerName: String, repoName: String) =
        iIssuesRepository.getRepoIssues(ownerName, repoName)
}