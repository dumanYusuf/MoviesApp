package com.example.filmlerapp.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.atilsamancioglu.cryptocrazycompose.util.Resource
import com.example.filmlerapp.model.Genre
import com.example.filmlerapp.model.ResultPopuler
import com.example.filmlerapp.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(private val repo:MoviesRepo ):ViewModel() {


    val moviesListCategory= mutableStateOf<List<Genre>>(listOf())
    val populerListCategory= mutableStateOf<List<ResultPopuler>>(listOf())
    val errorMessage= mutableStateOf("")
    val isLoading= mutableStateOf(false)

    init {
        loadMoviesCategory()
        loadPopulerMovies()
    }

    fun loadMoviesCategory(){
        viewModelScope.launch{
            isLoading.value=true
            val result=repo.resorceCategoryMovies()
            when(result){
                is Resource.Success->{
                    val categoryItem=result.data!!.genres.mapIndexed { index, item ->
                        Genre(item.id,item.name)
                    }
                    errorMessage.value=""
                    isLoading.value=false
                    moviesListCategory.value +=categoryItem
                }
                is Resource.Error->{
                    errorMessage.value=result.message!!
                }
                else->{
                    Log.e("viewModel","Not Data")
                }
            }

        }
    }

    fun loadPopulerMovies(){
        viewModelScope.launch {
            isLoading.value=true
            val result=repo.resourcePopulerMovies()
            when(result){
                is Resource.Success->{
                    val populerItem=result.data!!.results.mapIndexed { index, item ->
                     ResultPopuler(item.adult,item.backdrop_path,item.id,item.original_language,item.original_title,item.overview,item.popularity,item.poster_path,item.release_date,item.title,item.video,item.vote_average,item.vote_count)
                    }
                    errorMessage.value=""
                    isLoading.value=false
                    populerListCategory.value +=populerItem
                }
                is Resource.Error->{
                  errorMessage.value=result.message!!
                }
                else->{
                    Log.e("viewModel","not data")
                }
            }
        }
    }
}