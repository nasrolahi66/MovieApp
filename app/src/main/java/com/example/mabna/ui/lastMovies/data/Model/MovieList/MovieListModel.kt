package com.example.mabna.ui.lastMovies.data.Model.MovieList

data class MovieListModel(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

