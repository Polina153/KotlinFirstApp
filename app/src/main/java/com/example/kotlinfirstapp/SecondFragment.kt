package com.example.kotlinfirstapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SecondFragment : Fragment() {

    private lateinit var db: AppDatabase
    private var currentId = 1

    private lateinit var tvArtist: TextView
    private lateinit var tvPainting: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnNext: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvArtist = view.findViewById(R.id.tvArtist)
        tvPainting = view.findViewById(R.id.tvPainting)
        tvDescription = view.findViewById(R.id.tvDescription)
        btnNext = view.findViewById(R.id.btnNext)

       /* // Загрузка шрифта из assets
        val tf = Typeface.createFromAsset(requireActivity().assets, "1968odyssey3Dital.ttf")
        tvArtist.typeface = tf
        tvPainting.typeface = tf
        tvDescription.typeface = tf*/

        db = AppDatabase.getDatabase(requireActivity())

        lifecycleScope.launch {
            val dao = db.artworkDao()
            val count = dao.getArtworkById(1) // проверим элемент с ID 1
            if (count == null) {
                val artworks = listOf(
                    ArtistArtwork(1, "Leonardo da Vinci", "Mona Lisa", "A portrait of a woman known for her smile."),
                    ArtistArtwork(2, "Vincent van Gogh", "Starry Night", "A famous swirling night sky painting."),
                    ArtistArtwork(3, "Pablo Picasso", "Guernica", "A powerful anti-war mural."),
                    ArtistArtwork(4, "Claude Monet", "Water Lilies", "Series of beautiful water lily ponds."),
                    ArtistArtwork(5, "Salvador Dalí", "The Persistence of Memory", "Melting clocks in a desert landscape.")
                )
                dao.insertAll(artworks)
            }
            showArtwork(currentId)
        }

        btnNext.setOnClickListener {
            currentId++
            if (currentId > 5) currentId = 1
            lifecycleScope.launch{
                showArtwork(currentId)
            }
        }
    }

    private suspend fun showArtwork(id: Int) {
        val artwork = db.artworkDao().getArtworkById(id)
        artwork?.let {
            tvArtist.text = "Artist: ${it.artistName}"
            tvPainting.text = "Painting: ${it.paintingName}"
            tvDescription.text = "Description: ${it.description}"
        }
    }
}
