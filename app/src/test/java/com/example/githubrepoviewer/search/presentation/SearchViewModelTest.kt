package com.example.githubrepoviewer.search.presentation

import com.example.githubrepoviewer.repos.domain.models.RepoModel
import com.example.githubrepoviewer.repos.presentation.mappers.toRepoUiModel
import com.example.githubrepoviewer.search.data.repository.FakeSearchRepository
import com.example.githubrepoviewer.search.domain.usecases.SearchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var fakeSearchRepository: FakeSearchRepository
    private lateinit var searchUseCase: SearchUseCase

    private val repos = listOf(
        RepoModel(
            1, "GithubReposViewer", "mohamedadel2323", "android app to view github repositories", 0
        ),
        RepoModel(
            2, "Marketix", "abdelrahman_essam", "ecommerce android app", 3
        ),
        RepoModel(
            3, "TicTacToe", "hassankamal", "server client x o game", 5
        )
    )

    @Before
    fun setUp() {
        fakeSearchRepository = FakeSearchRepository(repos)
        searchUseCase = SearchUseCase(fakeSearchRepository)
        searchViewModel = SearchViewModel(searchUseCase, Dispatchers.Unconfined)
    }

    @Test
    fun searchForRepoWhenThereIsNetwork_firstItemFromTheListReturned() = runTest{
        // When: search function called with query "mohamed"
        searchViewModel.searchForRepo("mohamed")

        //Then: the returned list contain the first item of provided list and the owner name equal to "mohamedadel2323"
        val state = searchViewModel.reposState.value
        MatcherAssert.assertThat(state.searchRepos?.get(0), equalTo(repos[0].toRepoUiModel()))
        MatcherAssert.assertThat(state.searchRepos?.get(0)?.repoOwner ?: "", equalTo(repos[0].toRepoUiModel().repoOwner))
    }

    @Test
    fun searchForRepoWhenThereIsNoNetwork_errorMessageReturned() = runTest{
        //Given: Forcing the fake source to return an error
        fakeSearchRepository.setReturnError(true)

        // When: search function called with query "mohamed"
        searchViewModel.searchForRepo("mohamed")

        //Then: the returned list is null and the error message returned
        val state = searchViewModel.reposState.value
        MatcherAssert.assertThat(state.searchRepos, equalTo(null))
        MatcherAssert.assertThat(state.errorMessage, equalTo("Something went wrong, reconnect and retry."))
    }

    @Test
    fun errorMessageShown() = runTest{
        //Given: Forcing the fake source to return an error
        fakeSearchRepository.setReturnError(true)

        // When: search function called and error happened and called errorMessageShown function
        searchViewModel.searchForRepo("mohamed")
        searchViewModel.errorMessageShown()

        //Then: the returned error message is empty string
        MatcherAssert.assertThat(searchViewModel.reposState.value.errorMessage, equalTo(""))
    }

    @Test
    fun resetList() = runTest{
        // Given: search function called with query "mohamed" and the data received
        searchViewModel.searchForRepo("mohamed")
        var state = searchViewModel.reposState.value
        MatcherAssert.assertThat(state.searchRepos?.get(0), equalTo(repos[0].toRepoUiModel()))
        MatcherAssert.assertThat(state.searchRepos?.get(0)?.repoOwner ?: "", equalTo(repos[0].toRepoUiModel().repoOwner))

        //When: resetList function called
        searchViewModel.resetList()

        //Then: the list of search repos is empty
        state = searchViewModel.reposState.value
        MatcherAssert.assertThat(state.searchRepos, equalTo(listOf()))
    }
}