package com.example.githubrepoviewer.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepoviewer.details.domain.usecases.DetailsUseCase
import com.example.githubrepoviewer.details.presentation.mappers.toRepoDetailsUiModel
import com.example.githubrepoviewer.details.presentation.models.RepoDetailsUiModel
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
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsUseCase: DetailsUseCase,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _reposState: MutableStateFlow<DetailsScreenState> =
        MutableStateFlow(DetailsScreenState())
    val reposState: StateFlow<DetailsScreenState> = _reposState.asStateFlow()


    fun getRepo(owner: String, repoName: String) {
        viewModelScope.launch(ioDispatcher) {
            _reposState.update { it.copy(isReposLoading = true) }
            when (val result = detailsUseCase.getRepoDetails(owner, repoName)) {
                is Resource.Success -> {
                    _reposState.update {
                        it.copy(
                            repo = result.data?.toRepoDetailsUiModel() ?: RepoDetailsUiModel(
                                0,
                                "",
                                "",
                                "",
                                "",
                                0,
                                0,
                                0,
                                0
                            ), isReposLoading = false, networkCallCreated = true
                        )
                    }
                }

                is Resource.Failure -> {
                    _reposState.update {
                        it.copy(
                            errorMessage = result.error.toString(),
                            isReposLoading = false, networkCallCreated = true
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