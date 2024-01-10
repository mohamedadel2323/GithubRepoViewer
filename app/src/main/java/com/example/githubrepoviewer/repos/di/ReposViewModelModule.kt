package com.example.githubrepoviewer.repos.di

import com.example.githubrepoviewer.data.remote.details.RepoDetailsApiClient
import com.example.githubrepoviewer.data.remote.details.RepoDetailsRemoteSource
import com.example.githubrepoviewer.repos.data.local.ConcreteReposLocalSource
import com.example.githubrepoviewer.repos.data.local.ReposLocalSource
import com.example.githubrepoviewer.repos.data.remote.ReposApiClient
import com.example.githubrepoviewer.repos.data.remote.ReposRemoteSource
import com.example.githubrepoviewer.repos.data.repository.AllReposRepository
import com.example.githubrepoviewer.repos.domain.repository.IAllReposRepository
import com.example.githubrepoviewer.repos.domain.usecases.ReposUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReposViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindReposRemoteSource(reposApiClient: ReposApiClient): ReposRemoteSource

    @Binds
    @ViewModelScoped
    abstract fun bindReposDetailsRemoteSource(reposDetailsApiClient: RepoDetailsApiClient): RepoDetailsRemoteSource

    @Binds
    @ViewModelScoped
    abstract fun bindReposLocalSource(concreteLocalReposSource: ConcreteReposLocalSource): ReposLocalSource

    @Binds
    @ViewModelScoped
    abstract fun bindReposRepository(allReposRepository: AllReposRepository): IAllReposRepository

    companion object {
        @Provides
        @ViewModelScoped
        fun provideReposUseCase(iAllReposRepository: IAllReposRepository): ReposUseCase =
            ReposUseCase(iAllReposRepository)
    }

}