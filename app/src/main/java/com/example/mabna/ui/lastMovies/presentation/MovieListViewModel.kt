package com.example.mabna.ui.lastMovies.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mabna.ui.ResultData
import com.example.mabna.ui.lastMovies.data.Model.MovieDetail.MovieDetailModel
import com.example.mabna.ui.lastMovies.data.Model.MovieList.MovieListModel
import com.example.mabna.ui.lastMovies.domain.MovieListUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

public class MovieListViewModel @ViewModelInject constructor(val usecase: MovieListUseCase) : ViewModel() {
    var movieDetailJob: Job? = null
    var filteredMovieListJob: Job? = null
    var selectedDate: String? = null
    var mpage = 1
    val movieListLiveData = MutableLiveData<ResultData<MovieListModel>>()
    val movieDetialLiveData = MutableLiveData<ResultData<MovieDetailModel>>()


    private val movieDetialErrorHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        movieListLiveData.postValue(ResultData.Exception(throwable as Exception))
    }
    private val movieListErrorHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        movieListLiveData.postValue(ResultData.Exception(throwable as Exception))
    }

    fun getMovieDetail(movieId: Int) {
        movieDetailJob?.cancel()
        movieDetailJob = viewModelScope.launch(movieDetialErrorHandler) {
            movieDetialLiveData.postValue(ResultData.Loading(true))
            val result = usecase.getMovieDetail(movieId)
            movieDetialLiveData.postValue(ResultData.Success(result))
        }
    }

    fun getMovieList(page: Int, releaseDate: String? = null) {
        filteredMovieListJob?.cancel()
        filteredMovieListJob = viewModelScope.launch(movieListErrorHandler) {
            movieListLiveData.postValue(ResultData.Loading(true))
            var date = ""
            if (releaseDate != null)
                date = releaseDate;
            val result = usecase.getMovieList(page, date)
            movieListLiveData.postValue(ResultData.Success(result))
        }
    }
}