package com.example.kotlinfirstapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class SecondFragment : Fragment() {

    private lateinit var dbHelper: ArtDbHelper
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

        dbHelper = ArtDbHelper(requireContext())

        tvArtist = view.findViewById(R.id.tvArtist)
        tvPainting = view.findViewById(R.id.tvPainting)
        tvDescription = view.findViewById(R.id.tvDescription)
        btnNext = view.findViewById(R.id.btnNext)

        showArtwork(currentId)

        btnNext.setOnClickListener {
            currentId++
            if (currentId > 5) currentId = 1
            showArtwork(currentId)
        }
    }

    private fun showArtwork(id: Int) {
        val artwork = dbHelper.getArtworkById(id)
        artwork?.let {
            tvArtist.text = "Artist: ${it.artistName}"
            tvPainting.text = "Painting: ${it.paintingName}"
            tvDescription.text = "Description: ${it.description}"
        }
    }
}
