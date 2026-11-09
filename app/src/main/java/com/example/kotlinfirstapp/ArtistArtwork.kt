package com.example.kotlinfirstapp

import androidx.room.Entity
import androidx.room.PrimaryKey

/*data class ArtistArtwork(
    val id: Int,
    val artistName: String,
    val paintingName: String,
    val description: String
)*/

@Entity(tableName = "artworks")
data class ArtistArtwork(
    @PrimaryKey val id: Int,
    val artistName: String,
    val paintingName: String,
    val description: String
)
