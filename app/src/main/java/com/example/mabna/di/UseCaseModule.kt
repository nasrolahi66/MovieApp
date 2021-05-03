package com.example.mabna.di

import com.example.mabna.ui.lastMovies.data.MovieListRepositoryImpl
import com.example.mabna.ui.lastMovies.domain.MovieListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object UseCaseModule {
    @Provides
    fun provideLastMoviesUseCase(repositoryImpl: MovieListRepositoryImpl): MovieListUseCase {
        return MovieListUseCase(repositoryImpl)
    }
}