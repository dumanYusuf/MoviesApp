package com.example.filmlerapp.model

data class FilterCategoryMovies(
    val page: Int,
    val results: List<ResultFilterCategoryMovies>,
    val total_pages: Int,
    val total_results: Int
)