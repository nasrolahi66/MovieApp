package com.example.mabna.ui.lastMovies.domain

import com.example.mabna.ui.lastMovies.data.Model.MovieDetail.MovieDetailModel
import com.example.mabna.ui.lastMovies.data.Model.MovieList.MovieListModel


class MovieListUseCase(private val repository: MovieListIRepository) {
    suspend fun getMovieDetail(movieId: Int): MovieDetailModel {
        return repository.getMovieDetail(movieId)
    }

    suspend fun getMovieList(page: Int, releaseDate: String): MovieListModel {
        return repository.getMovieList(page, releaseDate)
    }
}