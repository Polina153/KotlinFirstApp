package com.example.kotlinfirstapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ArtDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY,
                $COL_ARTIST TEXT,
                $COL_PAINTING TEXT,
                $COL_DESCRIPTION TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)

        insertArtwork(db, 1, "Leonardo da Vinci", "Mona Lisa", "A portrait of a woman known for her smile.")
        insertArtwork(db, 2, "Vincent van Gogh", "Starry Night", "A famous swirling night sky painting.")
        insertArtwork(db, 3, "Pablo Picasso", "Guernica", "A powerful anti-war mural.")
        insertArtwork(db, 4, "Claude Monet", "Water Lilies", "Series of beautiful water lily ponds.")
        insertArtwork(db, 5, "Salvador Dalí", "The Persistence of Memory", "Melting clocks in a desert landscape.")
    }

    private fun insertArtwork(db: SQLiteDatabase, id: Int, artist: String, painting: String, description: String) {
        val values = ContentValues().apply {
            put(COL_ID, id)
            put(COL_ARTIST, artist)
            put(COL_PAINTING, painting)
            put(COL_DESCRIPTION, description)
        }
        db.insert(TABLE_NAME, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getArtworkById(id: Int): ArtistArtwork? {
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME,
            arrayOf(COL_ID, COL_ARTIST, COL_PAINTING, COL_DESCRIPTION),
            "$COL_ID = ?",
            arrayOf(id.toString()), null, null, null)

        var artwork: ArtistArtwork? = null
        if (cursor.moveToFirst()) {
            artwork = ArtistArtwork(
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_ARTIST)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_PAINTING)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION))
            )
        }
        cursor.close()
        return artwork
    }

    companion object {
        private const val DATABASE_NAME = "artworks.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "artworks"

        private const val COL_ID = "id"
        private const val COL_ARTIST = "artist"
        private const val COL_PAINTING = "painting"
        private const val COL_DESCRIPTION = "description"
    }
}
