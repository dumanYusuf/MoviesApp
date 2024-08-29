package com.example.filmlerapp.model

data class UpComing(
    val page: Int,
    val results: List<ResultUpComing>,
    val total_pages: Int,
    val total_results: Int
)