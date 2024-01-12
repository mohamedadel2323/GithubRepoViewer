package com.example.githubrepoviewer.issues.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepoviewer.issues.domain.usecases.IssuesUseCase
import com.example.githubrepoviewer.issues.presentation.mappers.toIssueUiModel
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
class IssuesViewModel @Inject constructor(
    private val issuesUseCase: IssuesUseCase,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _issuesState: MutableStateFlow<IssuesScreenState> =
        MutableStateFlow(IssuesScreenState())
    val issuesState: StateFlow<IssuesScreenState> = _issuesState.asStateFlow()

    fun getRepoIssues(owner: String, repoName: String) {
        viewModelScope.launch(ioDispatcher) {
            _issuesState.update { it.copy(isLoading = true) }
            issuesUseCase.getRepoIssues(owner, repoName).also { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { issuesList ->
                            _issuesState.update {
                                it.copy(
                                    issues = issuesList.map { issueModel -> issueModel.toIssueUiModel() },
                                    isLoading = false, networkCallCreated = true
                                )
                            }
                        }
                    }

                    is Resource.Failure -> {
                        resource.error?.let { errorMessage ->
                            _issuesState.update {
                                it.copy(
                                    errorMessage = errorMessage,
                                    isLoading = false, networkCallCreated = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun errorMessageShown() {
        _issuesState.update { it.copy(errorMessage = "") }
    }
}