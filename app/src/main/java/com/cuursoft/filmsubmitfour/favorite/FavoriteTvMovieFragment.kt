package com.cuursoft.filmsubmitfour.favorite


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuursoft.filmsubmitfour.DetailActivity
import com.cuursoft.filmsubmitfour.R
import com.cuursoft.filmsubmitfour.TvMovie
import com.cuursoft.filmsubmitfour.TvMovieAdapter
import com.cuursoft.filmsubmitfour.database.TvMovieHelper
import kotlinx.android.synthetic.main.fragment_favorite_tv_movie.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTvMovieFragment : Fragment() {

    lateinit var tvMovieHelper: TvMovieHelper
    lateinit var favoriteTvMovie: ArrayList<TvMovie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_movie, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvMovieHelper = TvMovieHelper.getInstance(context!!)
        tvMovieHelper.open()

        favoriteTvMovie = tvMovieHelper.getAllTvMOvie()

        recyclerV_favorite_tv_movie.apply {
            adapter = TvMovieAdapter(
                favoriteTvMovie,
                context,
                object : TvMovieAdapter.OnItemClicked {
                    override fun onItemClick(position: Int) {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("extra_type", "tv_movie_favorite")
                        intent.putExtra("fav_tv_movie", favoriteTvMovie[position])
                        startActivity(intent)
                    }
                })

            val reverseLinearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,
                true)
            reverseLinearLayoutManager.stackFromEnd = true
            layoutManager = reverseLinearLayoutManager

        }
        progressBar_favorite_tv_movie.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        favoriteTvMovie.clear()
        favoriteTvMovie.addAll(tvMovieHelper.getAllTvMOvie())
        recyclerV_favorite_tv_movie.adapter?.notifyDataSetChanged()
    }


}
