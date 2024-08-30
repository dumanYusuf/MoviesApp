package com.example.filmlerapp.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.filmlerapp.R
import com.example.filmlerapp.model.Genre
import com.example.filmlerapp.util.Constans
import com.example.filmlerapp.viewModel.CategoryFilterViewModel
import com.google.gson.Gson
import java.net.URLEncoder


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoryFilterMoviesPage(
    navController: NavController,
    genreID:Genre,
    viewModel:CategoryFilterViewModel= hiltViewModel()
)
{

    LaunchedEffect(key1 = true) {
        viewModel.loadfilterCategoryListMovies(genreID)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = genreID.name)})
        },
        content = {innerPadding->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 110.dp)
            ) {
                items(viewModel.filterCategoryMoviesList.value) { filter ->
                    Card (modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .size(300.dp)){
                        Row(
                            modifier = Modifier
                                .padding(5.dp)
                        ) {
                            val backdropPath = filter.backdrop_path
                            val imageUrl = "${Constans.BASE_IMAGE_URL}$backdropPath"

                            Box(modifier = Modifier.fillMaxWidth()){
                                Image(
                                    modifier = Modifier.fillMaxWidth().height(300.dp).clickable {
                                        val movieObject = Gson().toJson(filter)
                                        val encodedMovieObject = URLEncoder.encode(movieObject, "UTF-8")
                                        navController.navigate("detailPage/$encodedMovieObject")

                                    },
                                    painter = rememberImagePainter(data = imageUrl),
                                    contentDescription = "Movie Backdrop",
                                    contentScale = ContentScale.Crop
                                )
                                Log.e("imageUrl",imageUrl)
                                Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween){
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row {
                                            Icon(
                                                tint = Color.Yellow,
                                                painter = painterResource(id = R.drawable.calendar),
                                                contentDescription = ""
                                            )
                                            Text(
                                                color = Color.Yellow,
                                                text = filter.release_date
                                            )
                                        }
                                        Row {
                                            Icon(
                                                tint = Color.Yellow,
                                                painter = painterResource(id = R.drawable.star),
                                                contentDescription = ""
                                            )
                                            Text(
                                                color = Color.White,
                                                text = filter.vote_average.toString()
                                            )
                                        }
                                    }
                                    Column {
                                        Text(
                                            color = Color.Yellow,
                                            text = filter.original_title)
                                        Row {
                                            Icon(
                                                tint = Color.Red,
                                                painter = painterResource(id = R.drawable.flag), contentDescription = "")

                                            Spacer(modifier = Modifier.size(2.dp))
                                            Text(
                                                color = Color.Yellow,
                                                text = filter.original_language)
                                            Spacer(modifier = Modifier.size(5.dp))
                                            Text(
                                                color = Color.White,
                                                text = "Dublaj & Altyazı")
                                        }
                                    }
                                }
                            }
                        }
                    }


                }
            }
        }
    )
}