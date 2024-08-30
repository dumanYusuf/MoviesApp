package com.example.filmlerapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.filmlerapp.model.Genre
import com.example.filmlerapp.model.ResultPopuler
import com.example.filmlerapp.ui.theme.FilmlerAppTheme
import com.example.filmlerapp.view.CategoryFilterMoviesPage
import com.example.filmlerapp.view.DetailPage
import com.example.filmlerapp.view.HomePage
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilmlerAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    PageNavigator()
                }
            }
        }
    }
}

@Composable
fun PageNavigator(){
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination ="homePage") {
        composable("homePage"){
            HomePage(navController = navController)
        }
        composable("detailPage/{movie}",
            arguments = listOf(
                navArgument("movie"){type= NavType.StringType}
            )
        ){
            val jsonMovie = it.arguments?.getString("movie")
            val decodedJsonMovie = URLDecoder.decode(jsonMovie, "UTF-8")
            val movie = Gson().fromJson(decodedJsonMovie, ResultPopuler::class.java)
            DetailPage(movie = movie)

        }
        composable("filterPage/{genreId}",
            arguments = listOf(
                navArgument("genreId"){type=NavType.StringType}
            )
        ){
            val jsonGenreId=it.arguments?.getString("genreId")
            val genreId=Gson().fromJson(jsonGenreId,Genre::class.java)
            CategoryFilterMoviesPage(navController = navController, genreID = genreId)
        }
    }
}
