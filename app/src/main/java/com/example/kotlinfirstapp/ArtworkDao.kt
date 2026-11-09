package com.example.kotlinfirstapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArtworkDao {

    @Query("SELECT * FROM artworks WHERE id = :id")
    suspend fun getArtworkById(id: Int): ArtistArtwork?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(artworks: List<ArtistArtwork>)
}
