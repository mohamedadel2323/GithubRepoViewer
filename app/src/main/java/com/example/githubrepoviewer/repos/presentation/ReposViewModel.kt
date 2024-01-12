package com.example.githubrepoviewer.repos.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.collectLatest
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

    init {
        getAllRepos()
    }

    private fun getAllRepos() {
        viewModelScope.launch(ioDispatcher) {
            _reposState.update { it.copy(isReposLoading = true) }
            reposUseCase.getAllRepos().also { reposResource ->
                when (reposResource) {
                    is Resource.Success -> {
                        reposResource.data?.let { reposFlow ->
                            reposFlow.collectLatest { repos ->
                                if (repos.isNotEmpty()) {
                                    _reposState.update {
                                        it.copy(
                                            repos = repos.map { repoModel -> repoModel.toRepoUiModel() },
                                            isReposLoading = false
                                        )
                                    }
                                } else {
                                    _reposState.update {
                                        it.copy(
                                            errorMessage = "Something went wrong, reconnect and retry.",
                                            isReposLoading = false
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is Resource.Failure -> {
                        reposResource.error?.let { errorMessage ->
                            _reposState.update {
                                it.copy(
                                    errorMessage = errorMessage,
                                    isReposLoading = false
                                )
                            }
                        }
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
                    _reposState.update {
                        it.copy(
                            isReposLoading = false
                        )
                    }
                }

                is Resource.Failure -> {
                    _reposState.update {
                        it.copy(
                            errorMessage = result.error.toString(),
                            isReposLoading = false
                        )
                    }
                }
            }
        }
    }

    fun errorMessageShown() {
        _reposState.update { it.copy(errorMessage = "") }
    }
}