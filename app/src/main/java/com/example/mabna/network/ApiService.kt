package com.example.mabna.network

import com.example.mabna.network.NetworkConst.GET_FILTERED_BY_RELEASEDATE_MOVIES
import com.example.mabna.network.NetworkConst.GET_MOVIES_LIST
import com.example.mabna.network.NetworkConst.GET_MOVIE_DETAIL
import com.example.mabna.ui.lastMovies.data.Model.MovieDetail.MovieDetailModel
import com.example.mabna.ui.lastMovies.data.Model.MovieList.MovieListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ApiService {

    @GET(GET_MOVIE_DETAIL)
    suspend fun getMovieDetail(@Path("movie_id") movie_id: Int,
                               @Query("api_key") api_key: String): MovieDetailModel

    @GET(GET_FILTERED_BY_RELEASEDATE_MOVIES)
    suspend fun getMovieList(@Query("api_key") api_key: String,
                             @Query("page") page: Int,
                             @Query("release_date.lte") release_date: String): MovieListModel

}
