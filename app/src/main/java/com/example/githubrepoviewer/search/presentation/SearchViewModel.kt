package com.example.githubrepoviewer.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepoviewer.repos.presentation.mappers.toRepoUiModel
import com.example.githubrepoviewer.search.domain.usecases.SearchUseCase
import com.example.githubrepoviewer.utils.Dispatcher
import com.example.githubrepoviewer.utils.Dispatchers
import com.example.githubrepoviewer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel(){

    private val _searchState: MutableStateFlow<SearchScreenState> =
        MutableStateFlow(SearchScreenState())
    val reposState: StateFlow<SearchScreenState> = _searchState.asStateFlow()

    fun searchForRepo(query: String) {
        viewModelScope.launch(ioDispatcher) {
            _searchState.update { it.copy(isReposLoading = true) }
            when(val result = searchUseCase.searchForRepo(query)){
                is Resource.Success -> {
                    result.data?.let { reposList->
                        Timber.e(reposList.toString())
                        _searchState.update { screenState -> screenState.copy(searchRepos = reposList.map { it.toRepoUiModel() } , isReposLoading = false) }
                    }
                }

                is Resource.Failure -> {
                    _searchState.update {
                        it.copy(errorMessage = result.error.toString(), isReposLoading = false)
                    }
                }
            }
        }
    }

    fun errorMessageShown() {
        _searchState.update { it.copy(errorMessage = "") }
    }
    fun resetList(){
        _searchState.update { it.copy(searchRepos = listOf()) }
    }
}