package com.example.githubrepoviewer.repos.domain.usecases

import com.example.githubrepoviewer.repos.domain.models.RepoModel
import com.example.githubrepoviewer.repos.domain.repository.IAllReposRepository
import com.example.githubrepoviewer.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReposUseCase @Inject constructor(private val reposRepository: IAllReposRepository) {
    suspend fun getAllRepos(): Resource<Flow<List<RepoModel>>> = reposRepository.getAllRepos()
    suspend fun getDetails(owner: String, repoName: String) =
        reposRepository.getRepoDetails(owner, repoName)
}