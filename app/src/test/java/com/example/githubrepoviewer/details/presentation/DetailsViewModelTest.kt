package com.example.githubrepoviewer.details.presentation

import com.example.githubrepoviewer.details.data.FakeDetailsRepository
import com.example.githubrepoviewer.details.domain.models.RepoDetailsModel
import com.example.githubrepoviewer.details.domain.usecases.DetailsUseCase
import com.example.githubrepoviewer.details.presentation.mappers.toRepoDetailsUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class DetailsViewModelTest {

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var fakeDetailsRepository: FakeDetailsRepository
    private lateinit var detailsUseCase: DetailsUseCase

    private val repos = listOf(
        RepoDetailsModel(
            1, "GithubReposViewer", "mohamedadel2323", "android app to view github repositories", "kotlin",0,29,0,0
        ),
        RepoDetailsModel(
            2, "Marketix", "abdelrahman_essam", "ecommerce android app", "kotlin",3,32,3,0
        ),
        RepoDetailsModel(
            3, "TicTacToe", "hassankamal", "server client x o game", "java",5,50,5,0
        )
    )
    @Before
    fun setUp() {
        fakeDetailsRepository = FakeDetailsRepository(repos)
        detailsUseCase = DetailsUseCase(fakeDetailsRepository)
        detailsViewModel = DetailsViewModel(detailsUseCase, Dispatchers.Unconfined)
    }

    @Test
    fun getRepo() = runTest {
        //When getRepo fun called
        detailsViewModel.getRepo("abdelrahman_essam","Marketix")

        //then check the loaded function if it match
        val state = detailsViewModel.reposState.value
        MatcherAssert.assertThat(state.repo, equalTo(repos[1].toRepoDetailsUiModel()))
    }

    @Test
    fun errorMessageShown() = runTest {
        //Given: Forcing the fake source to return an error
        fakeDetailsRepository.setReturnError(true)

        // When: search function called and error happened and called errorMessageShown function
        detailsViewModel.getRepo("abdelrahman_essam","Marketix")
        detailsViewModel.errorMessageShown()

        //Then: the returned error message is empty string
        MatcherAssert.assertThat(detailsViewModel.reposState.value.errorMessage, equalTo(""))
    }
}