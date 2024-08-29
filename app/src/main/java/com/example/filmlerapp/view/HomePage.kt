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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.filmlerapp.R
import com.example.filmlerapp.util.Constans
import com.example.filmlerapp.viewModel.HomePageViewModel
import com.google.gson.Gson
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(
    navController: NavController,
    viewModel: HomePageViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Movies") })
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(viewModel.moviesListCategory.value) { category ->
                        Row(
                            modifier = Modifier
                                .padding(5.dp)
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Gray
                                ),
                                onClick = { }
                            ) {
                                Text(text = category.name)
                            }
                        }
                    }
                }

                Text(
                    text = "Populer Movies",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(5.dp)
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                ) {
                    items(viewModel.populerListCategory.value) { populerMovies ->
                        Card (modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .size(250.dp)){
                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                            ) {
                                val backdropPath = populerMovies.backdrop_path
                                val imageUrl = "${Constans.BASE_IMAGE_URL}$backdropPath"

                               Box(modifier = Modifier.fillMaxWidth()){
                                   Image(
                                       modifier = Modifier.size(250.dp).clickable {
                                           val movieObject = Gson().toJson(populerMovies)
                                           val encodedMovieObject = URLEncoder.encode(movieObject, "UTF-8")
                                           navController.navigate("detailPage/$encodedMovieObject")

                                       },
                                       painter = rememberImagePainter(data = imageUrl),
                                       contentDescription = "Movie Backdrop",
                                       contentScale = ContentScale.Crop
                                   )

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
                                                   text = populerMovies.release_date
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
                                                   text = populerMovies.vote_average.toString()
                                               )
                                           }
                                       }
                                       Column {
                                           Text(
                                               color = Color.Yellow,
                                               text = populerMovies.original_title)
                                           Row {
                                               Icon(
                                                   tint = Color.Red,
                                                   painter = painterResource(id = R.drawable.flag), contentDescription = "")

                                               Spacer(modifier = Modifier.size(2.dp))
                                               Text(
                                                   color = Color.Yellow,
                                                   text = populerMovies.original_language)
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

                Text(
                    fontSize = 24.sp,
                    modifier = Modifier.padding(5.dp),
                    text = "Top Rated")
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                ) {
                    items(viewModel.topRatedListCategory.value) { topRatedMovies ->
                        Log.e("LazyRowData", viewModel.topRatedListCategory.value.toString())
                        Card (modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .size(250.dp)){
                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                            ) {
                                val backdropPath = topRatedMovies.backdrop_path
                                val imageUrl = "${Constans.BASE_IMAGE_URL}$backdropPath"

                                Box(modifier = Modifier.fillMaxWidth()){
                                    Image(
                                        modifier = Modifier.size(250.dp).clickable {
                                            val movieObject = Gson().toJson(topRatedMovies)
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
                                                    text = topRatedMovies.release_date
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
                                                    text = topRatedMovies.vote_average.toString()
                                                )
                                            }
                                        }
                                        Column {
                                            Text(
                                                color = Color.Yellow,
                                                text = topRatedMovies.original_title)
                                            Row {
                                                Icon(
                                                    tint = Color.Red,
                                                    painter = painterResource(id = R.drawable.flag), contentDescription = "")

                                                Spacer(modifier = Modifier.size(2.dp))
                                                Text(
                                                    color = Color.Yellow,
                                                    text = topRatedMovies.original_language)
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

                Text(
                    fontSize = 24.sp,
                    modifier = Modifier.padding(5.dp),
                    text = "Up Coming")

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                ) {
                    items(viewModel.upComingList.value) { upComingList ->
                        Card (modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .size(250.dp)){
                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                            ) {
                                val backdropPath = upComingList.backdrop_path
                                val imageUrl = "${Constans.BASE_IMAGE_URL}$backdropPath"

                                Box(modifier = Modifier.fillMaxWidth()){
                                    Image(
                                        modifier = Modifier.size(250.dp).clickable {
                                            val movieObject = Gson().toJson(upComingList)
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
                                                    text = upComingList.release_date
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
                                                    text = upComingList.vote_average.toString()
                                                )
                                            }
                                        }
                                        Column {
                                            Text(
                                                color = Color.Yellow,
                                                text = upComingList.original_title)
                                            Row {
                                                Icon(
                                                    tint = Color.Red,
                                                    painter = painterResource(id = R.drawable.flag), contentDescription = "")

                                                Spacer(modifier = Modifier.size(2.dp))
                                                Text(
                                                    color = Color.Yellow,
                                                    text = upComingList.original_language)
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
        }
    )
}
