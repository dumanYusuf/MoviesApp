package com.example.filmlerapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import coil.compose.rememberImagePainter
import com.example.filmlerapp.R
import com.example.filmlerapp.model.ResultPopuler
import com.example.filmlerapp.util.Constans


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(movie:ResultPopuler){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = movie.original_title
                    )})
        },
        content = {innerPadding->
           Column(modifier = Modifier.fillMaxSize()) {
               Row (modifier = Modifier
                   .fillMaxWidth()
                   .padding(innerPadding)){
                   Text(
                       modifier = Modifier.padding(horizontal = 10.dp),
                       text = "Watching Movies")
                   Spacer(modifier = Modifier.size(5.dp))
                   Text(text = ">")
                   Spacer(modifier = Modifier.size(5.dp))
                   Text(text = "${movie.release_date} Movies")
               }

               Spacer(modifier = Modifier.size(10.dp))

               Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                   Button(
                       colors = ButtonDefaults.buttonColors(
                           containerColor = Color.Red
                       ),
                       onClick = { /*TODO*/ }) {
                       Text(
                           color = Color.White,
                           text = "IMDB: ${movie.vote_average}")
                   }
                   Spacer(modifier = Modifier.size(10.dp))
                   Button(
                       colors = ButtonDefaults.buttonColors(
                           containerColor = Color.Green
                       ),
                       onClick = { /*TODO*/ }) {
                       Text(
                           color = Color.White,
                           text = "Popularity: ${movie.popularity}")
                   }
               }
               Spacer(modifier = Modifier.size(10.dp))
               val backdropPath = movie.backdrop_path
               val imageUrl = "${Constans.BASE_IMAGE_URL}$backdropPath"
              Card(modifier = Modifier
                  .fillMaxWidth()
                  .size(250.dp)) {
                  Box(modifier = Modifier.fillMaxSize()){
                      Image(
                          modifier = Modifier
                              .fillMaxWidth()
                              .size(250.dp),
                          contentScale = ContentScale.Crop,
                          painter = rememberImagePainter(data =imageUrl), contentDescription = "")
                     Icon(
                          tint = Color.White,
                          modifier = Modifier
                              .padding()
                              .size(50.dp)
                              .align(Alignment.Center),
                          painter = painterResource(id = R.drawable.play), contentDescription ="" )
                  }
              }
               Spacer(modifier = Modifier.padding(10.dp))
               
               Text(
                   modifier = Modifier.padding(10.dp),
                   fontSize = 20.sp,
                   text = movie.overview)

           }
        }
    )
}