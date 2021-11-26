package com.example.myapplication

import androidx.appcompat.widget.SearchView

class MainHandler(funcCallBack:(String)->Boolean): SearchView.OnQueryTextListener {
    var aindaEstaPesquisando=false;
    val pesquisaPorNota = funcCallBack

    override fun onQueryTextSubmit(query: String?): Boolean {
        //TODO("Not yet implemented")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return pesquisaPorNota("$newText")
    }
}