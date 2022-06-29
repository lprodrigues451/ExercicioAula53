package br.com.zup.movieflix.moviedetail.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.movieflix.home.model.Movie
import br.com.zup.movieflix.moviedetail.datasource.DirectorLocalDataSource
import br.com.zup.movieflix.moviedetail.model.MovieWithDirectorModel
import br.com.zup.movieflix.moviedetail.repository.MovieDetailRepository

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovieDetailRepository(DirectorLocalDataSource())
    private var _response: MutableLiveData<MovieWithDirectorModel> = MutableLiveData()
    val response: LiveData<MovieWithDirectorModel> = _response

    fun getMovieWithDirector(movie: Movie) {
        try {
            _response.value = repository.getMovieWithDirector(movie)
        } catch (e: Exception) {
            Log.e("Erro", e.message.toString())
        }
    }

    private val pref: SharedPreferences =
        application.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
    private val prefEditor = pref.edit()

    fun saveFavorite(movieName: String, checked: Boolean) {
        prefEditor.putBoolean(movieName, checked)
        prefEditor.apply()
    }

    fun favorite(movieName: String): Boolean {
        return pref.getBoolean(movieName, false)
    }


}