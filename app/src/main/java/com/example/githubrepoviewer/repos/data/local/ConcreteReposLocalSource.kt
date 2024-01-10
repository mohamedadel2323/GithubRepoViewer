package com.example.githubrepoviewer.repos.data.local

import javax.inject.Inject

class ConcreteReposLocalSource @Inject constructor(private val reposDao: ReposDao) :
    ReposLocalSource {
    override suspend fun upsertAll(repos: List<RepoEntity>) = reposDao.upsertAll(repos)
    override fun getAllRepos() = reposDao.getAllRepos()
    override fun updateRepo(id: Int, currentStarCount: Int) = reposDao.updateRepo(id, currentStarCount)
}