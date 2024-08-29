package com.example.filmlerapp.servis

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

}