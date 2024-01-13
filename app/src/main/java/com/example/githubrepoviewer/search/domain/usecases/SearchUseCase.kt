package com.example.githubrepoviewer.search.domain.usecases

import com.example.githubrepoviewer.search.domain.repository.ISearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val searchRepository: ISearchRepository){
    suspend fun searchForRepo(query: String) = searchRepository.searchForRepos(query)
}