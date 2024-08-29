package com.example.filmlerapp.model

data class PopulerMovies(
    val page: Int,
    val results: List<ResultPopuler>,
    val total_pages: Int,
    val total_results: Int
)