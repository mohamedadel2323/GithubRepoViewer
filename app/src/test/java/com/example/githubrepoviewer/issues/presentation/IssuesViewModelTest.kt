package com.example.githubrepoviewer.issues.presentation

import com.example.githubrepoviewer.issues.data.FakeIssuesRepository
import com.example.githubrepoviewer.issues.domain.models.IssueModel
import com.example.githubrepoviewer.issues.domain.usecases.IssuesUseCase
import com.example.githubrepoviewer.issues.presentation.mappers.toIssueUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class IssuesViewModelTest {

    private lateinit var issuesViewModel: IssuesViewModel
    private lateinit var fakeIssuesRepository: FakeIssuesRepository
    private lateinit var issuesUseCase: IssuesUseCase
    private val issues = listOf(
        IssueModel(
            1,
            "issueTitle1",
            "author1",
            "1/12/2023",
            "1/13/2024",
            "1/14/2024",
            state = "closed"
        ),
        IssueModel(2, "issueTitle2", "author2", "1/11/2023", "1/12/2024", "", state = "open"),
        IssueModel(3, "issueTitle3", "author3", "1/10/2023", "1/11/2024", "", state = "open"),
        IssueModel(
            4,
            "issueTitle4",
            "author4",
            "1/9/2023",
            "1/10/2024",
            "1/11/2024",
            state = "closed"
        ),
    )

    @Before
    fun setUp() {
        fakeIssuesRepository = FakeIssuesRepository(issues)
        issuesUseCase = IssuesUseCase(fakeIssuesRepository)
        issuesViewModel = IssuesViewModel(issuesUseCase, Dispatchers.Unconfined)
    }


    @Test
    fun getRepoIssuesWhenThereIsNetwork_providedListReturned() = runTest {
        // When: search function called
        issuesViewModel.getRepoIssues("", "")

        //Then: the returned list is the same provided
        val state = issuesViewModel.issuesState.value
        MatcherAssert.assertThat(state.issues[0], CoreMatchers.equalTo(issues[0].toIssueUiModel()))
        MatcherAssert.assertThat(state.issues[0].author, CoreMatchers.equalTo(issues[0].toIssueUiModel().author))
    }

    @Test
    fun getRepoIssuesWhenThereIsNoNetwork_errorMessageReturned() = runTest {
        // When: search function called and forcing the error
        fakeIssuesRepository.setReturnError(true)
        issuesViewModel.getRepoIssues("", "")

        //Then: the returned list is empty and the error message returned
        val state = issuesViewModel.issuesState.value
        MatcherAssert.assertThat(state.issues, CoreMatchers.equalTo(listOf()))
        MatcherAssert.assertThat(state.errorMessage, CoreMatchers.equalTo("Something went wrong, reconnect and retry."))
    }

    @Test
    fun errorMessageShownCalled_theErrorMessageStateIsRested() {
        //Given: Forcing the fake source to return an error
        fakeIssuesRepository.setReturnError(true)

        // When: getIssues function called and error happened and called errorMessageShown function
        issuesViewModel.getRepoIssues("","")
        issuesViewModel.errorMessageShown()

        //Then: the returned error message is empty string
        MatcherAssert.assertThat(issuesViewModel.issuesState.value.errorMessage, CoreMatchers.equalTo(""))
    }
}