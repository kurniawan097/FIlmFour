package com.cuursoft.filmsubmitfour.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cuursoft.filmsubmitfour.R
import com.cuursoft.filmsubmitfour.SectionsPagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SectionsPagerAdapter(activity!!.supportFragmentManager)
        adapter.addFragment(MovieFragment(), getString(R.string.movie))
        adapter.addFragment(TvMovieFragment(), getString(R.string.tv_movie))

        view_pager_main.adapter = adapter
        tab_layout_main.setupWithViewPager(view_pager_main)

    }

}
