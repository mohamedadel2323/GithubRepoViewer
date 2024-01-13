package com.example.githubrepoviewer.repos.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.githubrepoviewer.repos.domain.usecases.ReposUseCase
import com.example.githubrepoviewer.repos.presentation.mappers.toRepoUiModel
import com.example.githubrepoviewer.utils.Dispatcher
import com.example.githubrepoviewer.utils.Dispatchers
import com.example.githubrepoviewer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val reposUseCase: ReposUseCase,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _reposState: MutableStateFlow<ReposScreenState> =
        MutableStateFlow(ReposScreenState())
    val reposState: StateFlow<ReposScreenState> = _reposState.asStateFlow()

    var repos = reposUseCase.getReposPagingData().cachedIn(viewModelScope)
        .map { pagingData -> pagingData.map { it.toRepoUiModel() } }

    init { getAllRepos() }

    private fun getAllRepos() {
        viewModelScope.launch(ioDispatcher) {
            _reposState.update { it.copy(isReposLoading = true) }
            reposUseCase.getAllRepos().also { reposResource ->
                when (reposResource) {
                    is Resource.Success -> {
                        _reposState.update { it.copy(isReposLoading = false) }
                        repos.collect {
                            _reposState.update { reposScreenState ->
                                reposScreenState.copy(repos = it)
                            }
                        }
                    }

                    is Resource.Failure -> {
                        _reposState.update { it.copy(errorMessage = reposResource.error ?: "", isReposLoading = false) }
                    }
                }
            }
        }
    }

    fun updateRepo(owner: String, repoName: String) {
        viewModelScope.launch(ioDispatcher) {
            _reposState.update { it.copy(isReposLoading = true) }
            when (val result = reposUseCase.getDetails(owner, repoName)) {
                is Resource.Success -> {
                    _reposState.update { it.copy(isReposLoading = false) }
                }

                is Resource.Failure -> {
                    _reposState.update { it.copy(errorMessage = result.error.toString(), isReposLoading = false) }
                }
            }
        }
    }

    fun errorMessageShown() {
        _reposState.update { it.copy(errorMessage = "") }
    }
}