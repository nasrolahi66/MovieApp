package com.example.mabna.ui.lastMovies.data

import com.example.mabna.network.ApiService
import com.example.mabna.ui.lastMovies.data.Model.MovieDetail.MovieDetailModel
import com.example.mabna.ui.lastMovies.data.Model.MovieList.MovieListModel
import com.example.mabna.ui.lastMovies.domain.MovieListIRepository
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(val apiService: ApiService) : MovieListIRepository {
    override suspend fun getMovieDetail(movieId: Int): MovieDetailModel {
        return apiService.getMovieDetail(movieId, "a5840262f7282a86ee51aa734a6927bd")
    }

    override suspend fun getMovieList(page: Int, releaseDate: String): MovieListModel {
        return apiService.getMovieList("a5840262f7282a86ee51aa734a6927bd", page, releaseDate)
    }

}