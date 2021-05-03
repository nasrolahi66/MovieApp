package com.example.mabna.ui.lastMovies.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mabna.R
import com.example.mabna.ui.lastMovies.data.Model.MovieDetail.MovieDetailModel
import com.example.mabna.ui.lastMovies.data.Model.MovieList.MovieListModel
import com.example.mabna.ui.lastMovies.data.Model.MovieList.Result
import com.example.mabna.utility.extentions.load
import kotlinx.android.synthetic.main.adater_item_movie.view.*

class MovieListRecyclerAdapter(private val callBack:(result:Result)->Unit) :
    ListAdapter<Result, MovieListRecyclerAdapter.MovieViewHolder>(DIFF_CALL_BACK) {
    companion object{
        val DIFF_CALL_BACK:DiffUtil.ItemCallback<Result> = object:
            DiffUtil.ItemCallback<Result>(){
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
 val view=LayoutInflater.from(parent.context).inflate(R.layout.adater_item_movie,parent,false)
        return MovieViewHolder(view,callBack)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    holder.onBind(currentList[position],position)
    }
    class MovieViewHolder(val item: View,val callBack: (result: Result) -> Unit) : RecyclerView.ViewHolder(item){
    fun onBind(movie: Result, position: Int) {
        item.txtMovieName.text=movie.title
        item.imgMovie.load("https://image.tmdb.org/t/p/original/" + movie.poster_path.toString())
        item.txtMovieReleaseDate.text=movie.release_date
       // val overview=HtmlCompat.fromHtml("<font size='5' color='#000000'>OverView :</font>",HtmlCompat.FROM_HTML_MODE_LEGACY)
        item.txtMovieOverview.text= "Overview:" + movie.overview
        item.txtMovieVoteAverage.text=movie.vote_average.toString()
        item.setOnClickListener { callBack(movie)

        }
    }
    }
}