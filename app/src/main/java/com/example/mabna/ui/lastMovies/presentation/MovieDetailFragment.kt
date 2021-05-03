package com.example.mabna.ui.lastMovies.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mabna.R
import com.example.mabna.ui.ResultData
import com.example.mabna.utility.extentions.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import java.text.NumberFormat
import java.util.*


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val viewModel by viewModels<MovieListViewModel>()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getInt("movie_id")
        viewModel.getMovieDetail(movieId!!)
        viewModel.movieDetialLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultData.Success -> {
                    val result = it.data
                    imgMovieDetail.load("https://image.tmdb.org/t/p/original/" + result.poster_path)
                    txtMovieName.text = result.title
                    val genresList = result.genres
                    var genresTitle = ""
                    genresList.forEach {
                        if (genresList.size == 1) genresTitle = it.name
                        else genresTitle += it.name + ","
                    }
                    txtMovieGenres.text = genresTitle
                    val budget = NumberFormat.getNumberInstance(Locale.US).format(result.budget)
                    txtMovieBudget.text = budget + "$"

                    var countryList = result.production_countries
                    var countryTitle = ""
                    countryList.forEach {
                        if (countryList.size == 1) countryTitle = it.name
                        else countryTitle += it.name
                    }
                    txtProductionCountries.text = countryTitle
                    txtMovieOriginalLanguage.text = result.original_language
                    var spokenLanguageList = result.production_countries
                    var spokenLanguageTitle = ""
                    spokenLanguageList.forEach {
                        if (spokenLanguageList.size == 1) spokenLanguageTitle = it.name
                        else spokenLanguageTitle += it.name
                    }
                    txtMovieSpokenLanguages.text = spokenLanguageTitle
                    txtMovieTime.text = result.runtime.toString() + " min"
                    txtMovieReleaseDate.text = result.release_date
                    txtMovieVoteAverage.text = result.vote_average.toString()
                    txtMovieOverview.text = "Overview:" + result.overview
                }
                is ResultData.Failed -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}