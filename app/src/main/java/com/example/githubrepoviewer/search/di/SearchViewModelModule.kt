package com.example.githubrepoviewer.search.di

import com.example.githubrepoviewer.search.data.remote.SearchRemoteClient
import com.example.githubrepoviewer.search.data.remote.SearchRemoteSource
import com.example.githubrepoviewer.search.data.repository.SearchRepository
import com.example.githubrepoviewer.search.domain.repository.ISearchRepository
import com.example.githubrepoviewer.search.domain.usecases.SearchUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchViewModelModule {
    @Binds
    @ViewModelScoped
    abstract fun bindSearchRemoteSource(searchRemoteClient: SearchRemoteClient): SearchRemoteSource

    @Binds
    @ViewModelScoped
    abstract fun bindSearchRepository(searchRepository: SearchRepository): ISearchRepository

    companion object {
        @Provides
        @ViewModelScoped
        fun provideSearchUseCase(iSearchRepository: ISearchRepository): SearchUseCase =
            SearchUseCase(iSearchRepository)
    }

}