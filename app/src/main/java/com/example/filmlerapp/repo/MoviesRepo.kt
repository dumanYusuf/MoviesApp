package com.example.filmlerapp.repo

import com.atilsamancioglu.cryptocrazycompose.util.Resource
import com.example.filmlerapp.model.FilterCategoryMovies
import com.example.filmlerapp.model.Genre
import com.example.filmlerapp.model.MoviesCategory
import com.example.filmlerapp.model.PopulerMovies
import com.example.filmlerapp.model.TopRated
import com.example.filmlerapp.model.UpComing
import com.example.filmlerapp.servis.MoviesApi
import com.example.filmlerapp.util.Constans
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MoviesRepo @Inject constructor(private val api:MoviesApi) {



    suspend fun <T> fetchResource(apiCall:suspend ()->T):Resource<T>{
        return try {
            val response=apiCall()
            Resource.Success(response)
        }
        catch (e:Exception){
            Resource.Error("Error")
        }
    }

    suspend fun resorceCategoryMovies():Resource<MoviesCategory>{
       return fetchResource { api.getMoviesCategory(Constans.API_KEY) }
    }

   suspend fun resourcePopulerMovies():Resource<PopulerMovies>{
       return fetchResource { api.getPopulerMovies(Constans.API_KEY) }
   }

    suspend fun resourseTopRated():Resource<TopRated>{
        return fetchResource { api.getTopRated(Constans.API_KEY) }
    }
    suspend fun resourceUpComing():Resource<UpComing>{
        return fetchResource { api.getUpComing(Constans.API_KEY) }
    }

    /*suspend fun resorceCategoryMovies():Resource<MoviesCategory>{
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

    suspend fun resourseTopRated():Resource<TopRated>{
        val response=try{
            api.getTopRated(Constans.API_KEY)
        }
        catch (e:Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

    suspend fun resourceUpComing():Resource<UpComing>{
        val response=try {
            api.getUpComing(Constans.API_KEY)
        }
        catch (e:Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }*/

    suspend fun resourceFilterCategoryMovies(category:Int):Resource<FilterCategoryMovies>{
        val response=try {
            api.getFilterCategoryMovies(Constans.API_KEY,category)
        }
        catch (e:Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

}