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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.filmlerapp.R
import com.example.filmlerapp.model.ResultPopuler
import com.example.filmlerapp.util.Constans
import com.example.filmlerapp.viewModel.HomePageViewModel
import com.google.gson.Gson
import java.net.URLEncoder
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(
    navController: NavController,
    viewModel: HomePageViewModel = hiltViewModel(),

) {
    val isSearcingStart= remember { mutableStateOf(false) }
    val tf=remember { mutableStateOf("") }
    Scaffold(
        topBar = {
           TopAppBar(title = {
               if (isSearcingStart.value){
                   TextField(
                       value = tf.value,
                       onValueChange = {
                           viewModel.searchMoviesResultPopulerList(it)
                           tf.value = it
                       },
                       label = { Text(text = "Searching Movies", color = Color.Gray) },
                       colors = TextFieldDefaults.textFieldColors(
                           focusedIndicatorColor = Color.White,
                           unfocusedIndicatorColor = Color.LightGray,
                           cursorColor = Color.White
                       ),
                       shape = RoundedCornerShape(8.dp),
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(16.dp),
                       leadingIcon = {
                           Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon", tint = Color.White)
                       },
                       trailingIcon = {
                           if (tf.value.isNotEmpty()) {
                               IconButton(onClick = { tf.value = "" }) {
                                   Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear Icon", tint = Color.White)
                               }
                           }
                       },
                       singleLine = true,

                   )

               }
               else{
                   CustomText(name = "Movies", size = 24.sp)
               }
           },
               actions = {
                   if (isSearcingStart.value){
                       IconButton(onClick = {
                           isSearcingStart.value=false
                           tf.value=""
                       }) {
                           Icon(painter = painterResource(id = R.drawable.close), contentDescription = "")
                       }
                   }
                   else{
                       IconButton(onClick = {
                           isSearcingStart.value=true
                       }) {
                           Icon(painter = painterResource(id = R.drawable.search), contentDescription = "")
                       }
                   }
               }
           )
        },
        content = { innerPadding ->
            if (viewModel.isLoading.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
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
                                    onClick = {
                                        val objectGenreId = Gson().toJson(category)
                                        navController.navigate("filterPage/$objectGenreId")
                                    }
                                ) {
                                    Text(text = category.name)
                                }
                            }
                        }
                    }

                    CustomText(name = "Populer Movies", size = 24.sp, modifier = Modifier.padding(5.dp))
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    ) {
                        items(viewModel.populerListCategory.value) { populerMovies ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .size(250.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(5.dp)
                                ) {
                                    val backdropPath = populerMovies.backdrop_path
                                    val imageUrl = "${Constans.BASE_IMAGE_URL}$backdropPath"

                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        Image(
                                            modifier = Modifier
                                                .size(250.dp)
                                                .clickable {
                                                    val movieObject = Gson().toJson(populerMovies)
                                                    val encodedMovieObject =
                                                        URLEncoder.encode(movieObject, "UTF-8")
                                                    navController.navigate("detailPage/$encodedMovieObject")
                                                },
                                            painter = rememberImagePainter(data = imageUrl),
                                            contentDescription = "Movie Backdrop",
                                            contentScale = ContentScale.Crop
                                        )

                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
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
                                                    text = populerMovies.original_title
                                                )
                                                Row {
                                                    Icon(
                                                        tint = Color.Red,
                                                        painter = painterResource(id = R.drawable.flag),
                                                        contentDescription = ""
                                                    )

                                                    Spacer(modifier = Modifier.size(2.dp))
                                                    Text(
                                                        color = Color.Yellow,
                                                        text = populerMovies.original_language
                                                    )
                                                    Spacer(modifier = Modifier.size(5.dp))
                                                    Text(
                                                        color = Color.White,
                                                        text = "Dublaj & Altyazı"
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    CustomText(name = "Top Rated", size = 24.sp, modifier = Modifier.padding(5.dp))
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    ) {
                        items(viewModel.topRatedListCategory.value) { topRatedMovies ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .size(250.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(5.dp)
                                ) {
                                    val backdropPath = topRatedMovies.backdrop_path
                                    val imageUrl = "${Constans.BASE_IMAGE_URL}$backdropPath"

                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        Image(
                                            modifier = Modifier
                                                .size(250.dp)
                                                .clickable {
                                                    val movieObject = Gson().toJson(topRatedMovies)
                                                    val encodedMovieObject =
                                                        URLEncoder.encode(movieObject, "UTF-8")
                                                    navController.navigate("detailPage/$encodedMovieObject")
                                                },
                                            painter = rememberImagePainter(data = imageUrl),
                                            contentDescription = "Movie Backdrop",
                                            contentScale = ContentScale.Crop
                                        )

                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
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
                                                    text = topRatedMovies.original_title
                                                )
                                                Row {
                                                    Icon(
                                                        tint = Color.Red,
                                                        painter = painterResource(id = R.drawable.flag),
                                                        contentDescription = ""
                                                    )

                                                    Spacer(modifier = Modifier.size(2.dp))
                                                    Text(
                                                        color = Color.Yellow,
                                                        text = topRatedMovies.original_language
                                                    )
                                                    Spacer(modifier = Modifier.size(5.dp))
                                                    Text(
                                                        color = Color.White,
                                                        text = "Dublaj & Altyazı"
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    CustomText(name = "Up Coming", size = 24.sp, modifier = Modifier.padding(7.dp))

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    ) {
                        items(viewModel.upComingList.value) { upComingList ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .size(250.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(5.dp)
                                ) {
                                    val backdropPath = upComingList.backdrop_path
                                    val imageUrl = "${Constans.BASE_IMAGE_URL}$backdropPath"

                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        Image(
                                            modifier = Modifier
                                                .size(250.dp)
                                                .clickable {
                                                    val movieObject = Gson().toJson(upComingList)
                                                    val encodedMovieObject =
                                                        URLEncoder.encode(movieObject, "UTF-8")
                                                    navController.navigate("detailPage/$encodedMovieObject")
                                                },
                                            painter = rememberImagePainter(data = imageUrl),
                                            contentDescription = "Movie Backdrop",
                                            contentScale = ContentScale.Crop
                                        )

                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
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
                                                    text = upComingList.original_title
                                                )
                                                Row {
                                                    Icon(
                                                        tint = Color.Red,
                                                        painter = painterResource(id = R.drawable.flag),
                                                        contentDescription = ""
                                                    )

                                                    Spacer(modifier = Modifier.size(2.dp))
                                                    Text(
                                                        color = Color.Yellow,
                                                        text = upComingList.original_language
                                                    )
                                                    Spacer(modifier = Modifier.size(5.dp))
                                                    Text(
                                                        color = Color.White,
                                                        text = "Dublaj & Altyazı"
                                                    )
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
        }

    )
}


@Composable
fun CustomText(name:String,size:TextUnit,modifier:Modifier= Modifier){
    Text(text = name, fontSize = size,modifier=modifier)
}









