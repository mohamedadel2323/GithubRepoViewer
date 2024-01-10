package com.example.githubrepoviewer.repos.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos_table")
data class RepoEntity(
    @PrimaryKey
    val id: Int,
    val repoName: String,
    val repoOwner: String,
    val description: String,
    val starCount: Int? = null,
)