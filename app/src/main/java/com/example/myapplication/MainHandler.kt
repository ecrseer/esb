package com.example.myapplication

import androidx.appcompat.widget.SearchView

class MainHandler(funcCallBack:(String)->Unit): SearchView.OnQueryTextListener {

    val pesquisaPorNota = funcCallBack

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        pesquisaPorNota("$newText")
        println("mudou")
        return true
    }
}