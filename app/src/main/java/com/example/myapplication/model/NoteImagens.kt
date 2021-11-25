package com.example.myapplication.model

object NoteImagens {
    var imgs  = mutableListOf(
        ImagemPesquisada("pg","nsei","olha eu    ","titulo2"),
        ImagemPesquisada("pg","nsei","olha eu com  ","titulo3"),
        ImagemPesquisada("pg","nsei","olha eu com bone","titulo4"),
        ImagemPesquisada("pg","nsei","olha eu sem bone","titulo5"),
        ImagemPesquisada("pg","nsei","olha eu com bone","titulo6"),
        ImagemPesquisada("pg","nsei","olha eu com bone","titulo7"),
        ImagemPesquisada("pg","nsei","olha eu com bone","titulo8"),
        ImagemPesquisada("pg","nsei","olha eu com bone","titulo9"),
        ImagemPesquisada("pg","nsei","olha eu com bone","titulo10"),

    )
    var imgsPeneiradas = mutableListOf<ImagemPesquisada>()

    val getNotaImgs =
        when(imgsPeneiradas.size){
         0 -> imgs
        else -> imgsPeneiradas
    }






}