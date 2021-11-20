package com.example.myapplication

import android.widget.Toast
import androidx.appcompat.widget.SearchView

class MainHandler: SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        NoteImagens.peneiraImagensPorTexto("$newText")
        println("mudou")
        return true
    }
}