package com.example.myapplication.model

object NoteImagens {
    var imgs  = mutableListOf<ImagemPesquisada>()
    var imgsPeneiradas = mutableListOf<ImagemPesquisada>()

    val getNotaImgs =
        when(imgsPeneiradas.size){
         0 -> imgs
        else -> imgsPeneiradas
    }






}