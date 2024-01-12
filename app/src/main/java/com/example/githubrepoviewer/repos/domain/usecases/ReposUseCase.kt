package com.example.githubrepoviewer.repos.domain.usecases

import com.example.githubrepoviewer.repos.domain.repository.IAllReposRepository
import javax.inject.Inject

class ReposUseCase @Inject constructor(private val reposRepository: IAllReposRepository) {
    suspend fun getAllRepos() = reposRepository.getAllRepos()
    suspend fun getDetails(owner: String, repoName: String) =
        reposRepository.getRepoDetails(owner, repoName)

    fun getReposPagingData() = reposRepository.getReposPagingData()
}