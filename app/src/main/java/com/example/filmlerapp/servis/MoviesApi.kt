package com.example.filmlerapp.servis

import com.example.filmlerapp.model.FilterCategoryMovies
import com.example.filmlerapp.model.MoviesCategory
import com.example.filmlerapp.model.PopulerMovies
import com.example.filmlerapp.model.TopRated
import com.example.filmlerapp.model.UpComing
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {


    // category
    @GET("genre/movie/list?language=en&api_key=9a618ccc9cc8813ecec78a18eaf88721")
    suspend fun getMoviesCategory(
        @Query("key") key:String
    ):MoviesCategory

    //pouler
    @GET("movie/popular?language=en-US&page=1&api_key=9a618ccc9cc8813ecec78a18eaf88721")
    suspend fun getPopulerMovies(
        @Query("key") key:String
    ):PopulerMovies

    // topRated
    @GET("movie/top_rated?language=en-US&page=1&api_key=9a618ccc9cc8813ecec78a18eaf88721")
    suspend fun getTopRated(
        @Query("key") key:String
    ):TopRated


    // upComing
    @GET("movie/upcoming?language=en-US&page=1&api_key=9a618ccc9cc8813ecec78a18eaf88721")
    suspend fun getUpComing(
        @Query("key") key:String
    ):UpComing

    // categoryFilterMovies

  /*  @GET("discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&api_key=9a618ccc9cc8813ecec78a18eaf88721")
    suspend fun getFilterCategoryMovies(
        @Query(value = "key") key: Int
    ):FilterCategoryMovies
*/

    @GET("discover/movie")
    suspend fun getFilterCategoryMovies(
        @Query("api_key") apiKey: String = "9a618ccc9cc8813ecec78a18eaf88721",
        @Query("with_genres") genreId: Int,
    ): FilterCategoryMovies
    //https://api.themoviedb.org/3/
// discover/movie?api_key=9a618ccc9cc8813ecec78a18eaf88721&with_genres=28&include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc
}