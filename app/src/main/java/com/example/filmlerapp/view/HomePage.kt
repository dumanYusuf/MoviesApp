package com.example.filmlerapp.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.filmlerapp.viewModel.HomePageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(
    viewModel:HomePageViewModel= hiltViewModel()
){

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Movies")})
        },
        content = { innerPadding->
            LazyRow (modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)){
                items(viewModel.moviesListCategory.value){category->
                   Row(modifier = Modifier
                       .fillMaxWidth()
                       .padding(5.dp)) {
                       Button(
                           colors = ButtonDefaults.buttonColors(
                               containerColor = Color.Gray
                           ),
                           onClick = { }) {
                           Text(text = category.name)

                       }
                   }
                }
            }
        }
    )

}