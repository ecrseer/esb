package com.example.myapplication

import ImagemPesquisada

object NoteImagens {
    var imgs  = mutableListOf(
        ImagemPesquisada("pg","nsei","olha eu sem bone","titulo2"),
        ImagemPesquisada("pg","nsei","olha eu com bone","titulo3")

    )
    var imgsPeneiradas = mutableListOf<ImagemPesquisada>()

    val getNotaImgs =
        when(imgsPeneiradas.size){
         0 -> imgs
        else -> imgsPeneiradas
    }

    fun peneiraImagensPorTexto(txt:String){

        for(notaImg in imgs){
            val contemTxtNoTexto = notaImg.texto.contains("$txt",true)
            val contemTxtNoTitulo = notaImg.texto.contains("$txt",true)
            if(contemTxtNoTexto || contemTxtNoTitulo){
                imgsPeneiradas.add(notaImg)
            }
        }

    }




}