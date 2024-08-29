package com.example.filmlerapp.model

data class TopRated(
    val page: Int,
    val results: List<ResultTopRated>,
    val total_pages: Int,
    val total_results: Int
)