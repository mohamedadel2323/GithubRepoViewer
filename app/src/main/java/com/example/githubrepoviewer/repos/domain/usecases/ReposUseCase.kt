package com.example.githubrepoviewer.repos.domain.usecases

import com.example.githubrepoviewer.repos.domain.models.RepoModel
import com.example.githubrepoviewer.repos.domain.repository.IAllReposRepository
import com.example.githubrepoviewer.utils.Resource
import javax.inject.Inject

class ReposUseCase @Inject constructor(private val reposRepository: IAllReposRepository) {
    suspend fun getAllRepos(): Resource<List<RepoModel>> = reposRepository.getAllRepos()
}