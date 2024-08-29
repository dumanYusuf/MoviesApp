package com.example.filmlerapp.servis

import com.example.filmlerapp.model.MoviesCategory
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {


    @GET("genre/movie/list?language=en&api_key=9a618ccc9cc8813ecec78a18eaf88721")
    suspend fun getMoviesCategory(
        @Query("key") key:String
    ):MoviesCategory

}