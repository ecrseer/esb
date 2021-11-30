package com.example.myapplication.model

object NoteImagens {
    var imgs  = mutableListOf(
        ImagemPesquisada(0,"_","_","olha eu    ","titulo2"),
        ImagemPesquisada(1,"_","_","olha eu com  ","titulo3"),
        ImagemPesquisada(2,"_","_","olha eu com bone","titulo4"),
        ImagemPesquisada(3,"_","_","olha eu sem bone","titulo5"),
        ImagemPesquisada(4,"_","_","olha eu com bone","titulo6"),
        ImagemPesquisada(5,"_","_","olha eu com bone","titulo7"),
        ImagemPesquisada(6,"_","_","olha eu com bone","titulo8"),
        ImagemPesquisada(7,"_","_","olha eu com bone","titulo9"),
        ImagemPesquisada(8,"_","_","olha eu com bone","titulo10"),

    )
    var imgsPeneiradas = mutableListOf<ImagemPesquisada>()

    val getNotaImgs =
        when(imgsPeneiradas.size){
         0 -> imgs
        else -> imgsPeneiradas
    }






}