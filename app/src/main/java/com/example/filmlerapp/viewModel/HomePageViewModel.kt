package com.example.filmlerapp.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.atilsamancioglu.cryptocrazycompose.util.Resource
import com.example.filmlerapp.model.Genre
import com.example.filmlerapp.model.ResultPopuler
import com.example.filmlerapp.model.ResultTopRated
import com.example.filmlerapp.model.ResultUpComing
import com.example.filmlerapp.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(private val repo:MoviesRepo ):ViewModel() {


    val moviesListCategory= mutableStateOf<List<Genre>>(listOf())
    val populerListCategory= mutableStateOf<List<ResultPopuler>>(listOf())
    val topRatedListCategory= mutableStateOf<List<ResultTopRated>>(listOf())
    val upComingList= mutableStateOf<List<ResultUpComing>>(listOf())
    val errorMessage= mutableStateOf("")
    val isLoading= mutableStateOf(false)

    private var initialMovieList= listOf<ResultPopuler>()// bu bana inmis olan populer listeyi verecek
    private var isSearcingStarting=true


    init {
        loadMoviesCategory()
        loadPopulerMovies()
        loadTopRatedMovies()
        loadUpComing()
    }





    fun searchMoviesResultPopulerList(query:String){
        val listToSearch=if (isSearcingStarting){
            populerListCategory.value
        }
        else{
            initialMovieList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()){
                populerListCategory.value=initialMovieList
                isSearcingStarting=true
                return@launch
            }

            val results=listToSearch.filter {
                it.title.contains(query.trim(), ignoreCase = true)// ignorcase büyük kücük farkEtmiyor
            }
            if (isSearcingStarting){
                initialMovieList=populerListCategory.value
                isSearcingStarting=false
            }

            populerListCategory.value=results
        }
    }




    /* fun loadMoviesCategory(){
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

     fun loadTopRatedMovies(){
         viewModelScope.launch {
             isLoading.value=true
             val result=repo.resourseTopRated()
             when(result){
                 is Resource.Success->{
                     val categoryItem=result.data!!.results.mapIndexed { index, item ->
                         ResultTopRated(item.adult,item.backdrop_path,item.id,item.original_language,item.original_title,item.overview,item.popularity,item.poster_path,item.release_date,item.title,item.video,item.vote_average,item.vote_count)
                     }
                     Log.e("viewModel","topRated succsess")

                     errorMessage.value=""
                     isLoading.value=false
                     topRatedListCategory.value +=categoryItem
                 }
                 is Resource.Error->{
                     errorMessage.value=result.message!!
                 }
                 else->{
                     Log.e("viewModel","top Rated Error")
                 }
             }
         }
     }

     fun loadUpComing(){
         viewModelScope.launch {
             isLoading.value=true
             val result=repo.resourceUpComing()
             when(result){
                 is Resource.Success->{
                     val categoryItem=result.data!!.results.mapIndexed { index, item ->
                         ResultUpComing(item.adult,item.backdrop_path,item.id,item.original_language,item.original_title,item.overview,item.popularity,item.poster_path,item.release_date,item.title,item.video,item.vote_average,item.vote_count)
                     }
                     Log.e("viewModel","topRated succsess")

                     errorMessage.value=""
                     isLoading.value=false
                     upComingList.value +=categoryItem
                 }
                 is Resource.Error->{
                     errorMessage.value=result.message!!
                 }
                 else->{
                     Log.e("viewModel","top Rated Error")
                 }
             }
         }
     }
 */

    fun <T, R> loadData(
        resultList: MutableState<List<R>>,
        dataFetcher: suspend () -> Resource<T>,
        mapper: (T) -> List<R>,
        errorMessage: MutableState<String>
    ) {
        viewModelScope.launch {
            isLoading.value = true
            val result = dataFetcher()
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        val items = mapper(it)
                        resultList.value = items
                    }
                    errorMessage.value = ""
                    isLoading.value = false
                }
                is Resource.Error -> {
                    errorMessage.value = result.message ?: "An error occurred"
                    isLoading.value = false
                }
                else -> {
                    Log.e("ViewModel", "Unexpected result")
                    isLoading.value = false
                }
            }
        }
    }
    fun loadMoviesCategory() {
        loadData(
            resultList = moviesListCategory,
            dataFetcher = { repo.resorceCategoryMovies() },
            mapper = { result -> result.genres.map { Genre(it.id, it.name) } },
            errorMessage = errorMessage
        )
    }

    fun loadPopulerMovies() {
        loadData(
            resultList = populerListCategory,
            dataFetcher = { repo.resourcePopulerMovies() },
            mapper = { result -> result.results.map { ResultPopuler(it.adult, it.backdrop_path, it.id, it.original_language, it.original_title, it.overview, it.popularity, it.poster_path, it.release_date, it.title, it.video, it.vote_average, it.vote_count) } },
            errorMessage = errorMessage
        )
    }

    fun loadTopRatedMovies() {
        loadData(
            resultList = topRatedListCategory,
            dataFetcher = { repo.resourseTopRated() },
            mapper = { result -> result.results.map { ResultTopRated(it.adult, it.backdrop_path, it.id, it.original_language, it.original_title, it.overview, it.popularity, it.poster_path, it.release_date, it.title, it.video, it.vote_average, it.vote_count) } },
            errorMessage = errorMessage
        )
    }

    fun loadUpComing() {
        loadData(
            resultList = upComingList,
            dataFetcher = { repo.resourceUpComing() },
            mapper = { result -> result.results.map { ResultUpComing(it.adult, it.backdrop_path, it.id, it.original_language, it.original_title, it.overview, it.popularity, it.poster_path, it.release_date, it.title, it.video, it.vote_average, it.vote_count) } },
            errorMessage = errorMessage
        )
    }

}