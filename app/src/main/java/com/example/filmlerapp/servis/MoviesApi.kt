package com.example.filmlerapp.servis

import com.example.filmlerapp.model.MoviesCategory
import com.example.filmlerapp.model.PopulerMovies
import com.example.filmlerapp.model.TopRated
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {


    @GET("genre/movie/list?language=en&api_key=9a618ccc9cc8813ecec78a18eaf88721")
    suspend fun getMoviesCategory(
        @Query("key") key:String
    ):MoviesCategory

    @GET("movie/popular?language=en-US&page=1&api_key=9a618ccc9cc8813ecec78a18eaf88721")
    suspend fun getPopulerMovies(
        @Query("key") key:String
    ):PopulerMovies

    @GET("movie/top_rated?language=en-US&page=1&api_key=9a618ccc9cc8813ecec78a18eaf88721")
    suspend fun getTopRated(
        @Query("key") key:String
    ):TopRated

}