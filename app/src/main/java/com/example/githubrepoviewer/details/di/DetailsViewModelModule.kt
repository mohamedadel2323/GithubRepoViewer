package com.example.githubrepoviewer.details.di

import com.example.githubrepoviewer.details.data.repository.DetailsRepository
import com.example.githubrepoviewer.details.domain.repository.IRepositoryDetails
import com.example.githubrepoviewer.details.domain.usecases.DetailsUseCase

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DetailsViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindReposRepository(detailsRepository: DetailsRepository): IRepositoryDetails

    companion object {
        @Provides
        @ViewModelScoped
        fun provideDetailsUseCase(iRepositoryDetails: IRepositoryDetails): DetailsUseCase =
            DetailsUseCase(iRepositoryDetails)
    }
}