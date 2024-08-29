package com.example.filmlerapp.repo

import com.atilsamancioglu.cryptocrazycompose.util.Resource
import com.example.filmlerapp.model.MoviesCategory
import com.example.filmlerapp.model.PopulerMovies
import com.example.filmlerapp.servis.MoviesApi
import com.example.filmlerapp.util.Constans
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MoviesRepo @Inject constructor(private val api:MoviesApi) {


    suspend fun resorceCategoryMovies():Resource<MoviesCategory>{
        val response=try {
            api.getMoviesCategory(Constans.API_KEY)
        }
        catch (e:Exception){
           return Resource.Error("Error")
        }
        return Resource.Success(response)
    }


    suspend fun resourcePopulerMovies():Resource<PopulerMovies>{
        val response=try{
            api.getPopulerMovies(Constans.API_KEY)
        }
        catch (e:Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

}