package com.cuursoft.filmsubmitfour

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class Movie (
    var id: String,
    var title: String,
    var releaseDate: String,
    var score: String,
    var description: String,
    var poster: String,
    var backdrop: String
) : Parcelable{

    constructor(`object`: JSONObject) : this(
        `object`.getString("id"),
        `object`.getString("title"),
        `object`.getString("release_date"),
        `object`.getString("vote_average"),
        `object`.getString("overview"),
        `object`.getString("poster_path"),
        `object`.getString("backdrop_path")
    )



}