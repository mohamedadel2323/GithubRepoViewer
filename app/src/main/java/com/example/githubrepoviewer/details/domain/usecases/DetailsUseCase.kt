package com.example.githubrepoviewer.details.domain.usecases

import com.example.githubrepoviewer.details.domain.repository.IRepositoryDetails
import javax.inject.Inject

class DetailsUseCase @Inject constructor(private val detailsRepository: IRepositoryDetails) {
    suspend fun getRepoDetails(owner: String, repoName: String) =
        detailsRepository.getRepoDetails(owner, repoName)
}