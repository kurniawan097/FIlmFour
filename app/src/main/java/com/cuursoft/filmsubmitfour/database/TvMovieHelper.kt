package com.cuursoft.filmsubmitfour.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.cuursoft.filmsubmitfour.TvMovie
import com.cuursoft.filmsubmitfour.database.DatabaseContract.TvMovieColumns.Companion.BACKDROP
import com.cuursoft.filmsubmitfour.database.DatabaseContract.TvMovieColumns.Companion.DESCRIPTION
import com.cuursoft.filmsubmitfour.database.DatabaseContract.TvMovieColumns.Companion.FIRST_AIR_DATE
import com.cuursoft.filmsubmitfour.database.DatabaseContract.TvMovieColumns.Companion.ID
import com.cuursoft.filmsubmitfour.database.DatabaseContract.TvMovieColumns.Companion.POSTER
import com.cuursoft.filmsubmitfour.database.DatabaseContract.TvMovieColumns.Companion.SCORE
import com.cuursoft.filmsubmitfour.database.DatabaseContract.TvMovieColumns.Companion.TITLE
import java.sql.SQLException

class TvMovieHelper(context: Context) {
    val DATABASE_TABLE = DatabaseContract().TABLE_TV_MOVIE
    var databaseHelper = DatabaseHelper(context)
    lateinit var database: SQLiteDatabase

    companion object {

        @Volatile private var INSTANCE: TvMovieHelper? = null

        fun getInstance(context: Context): TvMovieHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TvMovieHelper(context)
            }

    }


    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen())
            database.close()
    }

    fun getAllTvMOvie(): ArrayList<TvMovie> {
        val arrayList = ArrayList<TvMovie>()
        val cursor = database.query(
            DATABASE_TABLE,
            null, null, null, null, null,
            null, null
        )
        cursor.moveToFirst()
        var tvMovie: TvMovie
        if (cursor.count > 0) {
            do {
                tvMovie = TvMovie(
                    cursor.getString(cursor.getColumnIndexOrThrow(ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FIRST_AIR_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SCORE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(POSTER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP))
                )

                arrayList.add(tvMovie)
                cursor.moveToNext()

            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    fun isTvMovieFavorited(id: String): Boolean {
        val cursor = database.query(
            DATABASE_TABLE,
            null, "$ID = '$id'", null, null, null,
            null, null
        )
        cursor.moveToFirst()
        if (cursor.count > 0) {
            return true
        }
        return false

    }

    fun insertTvMovie(tvMovie: TvMovie): Long {
        val args = ContentValues()
        Log.d("TES123", "id insert = "+tvMovie.id)
        args.put(ID, tvMovie.id)
        args.put(TITLE, tvMovie.title)
        args.put(FIRST_AIR_DATE, tvMovie.releaseDate)
        args.put(SCORE, tvMovie.score)
        args.put(DESCRIPTION, tvMovie.description)
        args.put(POSTER, tvMovie.poster)
        args.put(BACKDROP, tvMovie.backdrop)
        return database.insert(DATABASE_TABLE, null, args)
    }

    fun deleteTvMovie(id: String): Int {
        return database.delete(DatabaseContract().TABLE_TV_MOVIE, "$ID = '$id'", null)
    }

}