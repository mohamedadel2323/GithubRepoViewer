package com.example.githubrepoviewer.search.data.dtos

import com.example.githubrepoviewer.details.data.dtos.RepoDetailsResponse

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<RepoDetailsResponse>,
    val total_count: Int
)