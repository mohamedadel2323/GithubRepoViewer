package com.example.githubrepoviewer.repos.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ReposDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ReposDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ReposDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun upsertAll() = runTest {
        //Given - insert list of repos
        val reposEntities = listOf(
            RepoEntity(
                1, "GithubReposViewer", "mohamedadel2323", "android app to view github repositories", null
            ),
            RepoEntity(
                2, "Marketix", "abdelrahman_essam", "ecommerce android app", null
            ),
            RepoEntity(
                3, "TicTacToe", "hassankamal", "server client x o game", null
            )
        )
        database.getRepoSDao().upsertAll(reposEntities)

        //When get all repos:
        val loadedRepos = database.getRepoSDao().getRepos()

        //Then assert that the inserted equal to the retrieved
        MatcherAssert.assertThat(reposEntities, equalTo(loadedRepos))
    }

    @Test
    fun updateRepo() = runTest{
        //Given - insertd list of repos
        val reposEntities = listOf(
            RepoEntity(
                1, "GithubReposViewer", "mohamedadel2323", "android app to view github repositories", null
            ),
            RepoEntity(
                2, "Marketix", "abdelrahman_essam", "ecommerce android app", null
            ),
            RepoEntity(
                3, "TicTacToe", "hassankamal", "server client x o game", null
            )
        )
        database.getRepoSDao().upsertAll(reposEntities)

        //When updateRepo Called and pass 5 to the star count
        database.getRepoSDao().updateRepo(1, 5)

        //Then get all repos and check the update
        val loadedRepos = database.getRepoSDao().getRepos()
        MatcherAssert.assertThat(loadedRepos[0].starCount, equalTo(5))
    }
}