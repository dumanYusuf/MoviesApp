package com.example.filmlerapp.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atilsamancioglu.cryptocrazycompose.util.Resource
import com.example.filmlerapp.model.Genre
import com.example.filmlerapp.model.ResultFilterCategoryMovies
import com.example.filmlerapp.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryFilterViewModel @Inject constructor(private val repo:MoviesRepo): ViewModel() {

    val filterCategoryMoviesList= mutableStateOf<List<ResultFilterCategoryMovies>>(listOf())
    val errorMessage= mutableStateOf("")
    val isLoading= mutableStateOf(false)

    /*init {
        loadfilterCategoryListMovies()
    }*/

    fun loadfilterCategoryListMovies(genreId:Genre){
        viewModelScope.launch{
            isLoading.value=true
            val result=repo.resourceFilterCategoryMovies(category = genreId.id)
            when(result){
                is Resource.Success->{
                    val categoryItem=result.data!!.results.mapIndexed { index, item ->
                        ResultFilterCategoryMovies(item.adult,item.backdrop_path,item.genre_ids,item.id,item.original_language,item.original_title,item.overview,item.popularity,item.poster_path,item.release_date,item.title,item.video,item.vote_average,item.vote_count)
                    }
                    errorMessage.value=""
                    isLoading.value=false
                    filterCategoryMoviesList.value +=categoryItem
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