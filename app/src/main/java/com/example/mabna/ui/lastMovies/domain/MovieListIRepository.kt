package com.example.mabna.ui.lastMovies.domain

import com.example.mabna.ui.lastMovies.data.Model.MovieDetail.MovieDetailModel
import com.example.mabna.ui.lastMovies.data.Model.MovieList.MovieListModel
import retrofit2.Response
import java.util.*

interface MovieListIRepository {
    suspend fun getMovieDetail(movieId:Int):MovieDetailModel
    suspend fun getMovieList(page:Int, releaseDate: String):MovieListModel
}