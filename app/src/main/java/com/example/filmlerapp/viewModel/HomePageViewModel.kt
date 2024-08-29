package com.example.filmlerapp.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.atilsamancioglu.cryptocrazycompose.util.Resource
import com.example.filmlerapp.model.Genre
import com.example.filmlerapp.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(private val repo:MoviesRepo ):ViewModel() {


    val moviesListCategory= mutableStateOf<List<Genre>>(listOf())
    val errorMessage= mutableStateOf("")
    val isLoading= mutableStateOf(false)

    init {
        loadMoviesCategory()
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
}