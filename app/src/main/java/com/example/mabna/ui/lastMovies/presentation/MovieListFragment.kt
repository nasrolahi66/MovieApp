package com.example.mabna.ui.lastMovies.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mabna.R
import com.example.mabna.ui.ResultData
import com.example.mabna.ui.lastMovies.data.Model.MovieList.Result
import com.example.mabna.ui.lastMovies.presentation.bottomSheet.DatePickerBottomSheet
import com.example.mabna.utility.EndlessRecyclerViewScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_list.*

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val viewModel by viewModels<MovieListViewModel>()
    val adapter = MovieListRecyclerAdapter(::MovieListItemClicked)
    var mItems = mutableListOf<Result>()
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var isMoreData = true
    private var itemCount: Int = 20
    lateinit var datePickerBottomSheet: DatePickerBottomSheet
    lateinit var navController: NavController
    var movieListHasLoadedOnce = false

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) adapter.notifyDataSetChanged()
        super.onHiddenChanged(hidden)
    }
//    }

    override fun onResume() {
        if (!movieListHasLoadedOnce)
            viewModel.getMovieList(viewModel.mpage, viewModel.selectedDate)
        movieListHasLoadedOnce = true
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setListeneres()
        initRecyclerView()
        initDatePicker()
        observeFilteredMovieListLiveData()
    }

    private fun observeFilteredMovieListLiveData() {
        viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultData.Loading -> {
                    progressBar.isVisible = true
                }
                is ResultData.Success -> {
                    progressBar.isVisible = false
                    if (viewModel.selectedDate != null) {
                        txtSelectedReleaseDate.text = "Producted Movies Befor  " + viewModel.selectedDate
                        txtSelectedReleaseDate.isVisible = true
                        imgRemoveFilter.isVisible = true
                    }
                    if (it.data.results.isEmpty()) {
                        isMoreData = false
                    } else {
                        if (viewModel.mpage == 1)
                            itemCount = it.data.results.size

                        isMoreData = it.data.results.size >= itemCount

                        it.data.results.forEach {
                            mItems.add(it)
                        }
                        adapter.submitList(mItems)
                        adapter.notifyDataSetChanged()
                    }
                }
                is ResultData.Failed -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    progressBar.isVisible = false
                }
            }
        })
    }

    private fun initDatePicker() {
        datePickerBottomSheet = DatePickerBottomSheet(::onSelectedDate)
    }

    private fun setListeneres() {
        swipeRefresh.setOnRefreshListener {
            viewModel.mpage = 1
            mItems.clear()
            isMoreData = true
            scrollListener?.resetState()
            viewModel.getMovieList(viewModel.mpage, viewModel.selectedDate)
            swipeRefresh.isRefreshing = false
        }
        txtFilter.setOnClickListener {
            if (::datePickerBottomSheet.isInitialized && !datePickerBottomSheet.isAdded)
                activity?.run {
                    datePickerBottomSheet.show(
                            supportFragmentManager, "DatePickerBottomSheet"
                    )
                }
        }
        imgRemoveFilter.setOnClickListener {
            txtSelectedReleaseDate.isVisible = false
            imgRemoveFilter.isVisible = false
            viewModel.selectedDate = null
            viewModel.mpage = 1
            mItems.clear()
            isMoreData = true
            scrollListener?.resetState()
            viewModel.getMovieList(viewModel.mpage)
        }
    }

    private fun initRecyclerView() {
        val mLayoutManager = LinearLayoutManager(requireContext())
        scrollListener = object : EndlessRecyclerViewScrollListener(mLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.mpage = page
                if (isMoreData)
                    viewModel.getMovieList(viewModel.mpage, viewModel.selectedDate)
            }
        }
        recycler.adapter = adapter
        recycler.layoutManager = mLayoutManager
        recycler.addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)
    }

    private fun MovieListItemClicked(result: Result) {
        val bundle = bundleOf("movie_id" to result.id)
        navController.navigate(R.id.action_movieListFragment_to_movieDetailFragment, bundle)
    }

    private fun onSelectedDate(date: String) {
        viewModel.selectedDate = date
        viewModel.mpage = 1
        mItems.clear()
        isMoreData = true
        scrollListener?.resetState()
        viewModel.getMovieList(viewModel.mpage, date)
    }
}


