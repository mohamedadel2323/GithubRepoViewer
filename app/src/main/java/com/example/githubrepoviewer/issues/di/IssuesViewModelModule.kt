package com.example.githubrepoviewer.issues.di

import com.example.githubrepoviewer.issues.data.remote.IssuesRemoteClient
import com.example.githubrepoviewer.issues.data.remote.IssuesRemoteSource
import com.example.githubrepoviewer.issues.data.repository.IssuesRepository
import com.example.githubrepoviewer.issues.domain.repository.IIssuesRepository
import com.example.githubrepoviewer.issues.domain.usecases.IssuesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class IssuesViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindIssuesRemoteSource(issuesRemoteClient: IssuesRemoteClient): IssuesRemoteSource

    @Binds
    @ViewModelScoped
    abstract fun bindIssuesRepository(issuesRepository: IssuesRepository): IIssuesRepository

    companion object {
        @Provides
        @ViewModelScoped
        fun provideIssuesUseCase(iIssuesRepository: IIssuesRepository) =
            IssuesUseCase(iIssuesRepository)
    }
}