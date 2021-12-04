package com.example.myapplication.domain

object PersistenciaDadosNotas {
    var imgs:MutableList<ImagemNota>  = mutableListOf<ImagemNota>(
        ImagemNota(0,"_","_","olha eu    ","titulo2"),
        ImagemNota(1,"_","_","olha eu com  ","titulo3"),
        ImagemNota(2,"_","_","olha eu com bone","titulo4"),
        ImagemNota(3,"_","_","olha eu sem bone","titulo5"),
        ImagemNota(4,"_","_","olha eu com bone","titulo6"),
        ImagemNota(5,"_","_","olha eu com bone","titulo7"),
        ImagemNota(6,"_","_","olha eu com bone","titulo8"),
        ImagemNota(7,"_","_","olha eu com bone","titulo9"),
        ImagemNota(8,"_","_","olha eu com bone","titulo10"),

        )
    var imgsPeneiradas:List<ImagemNota> = mutableListOf<ImagemNota>()

    var imgsFavoritas = mutableListOf<ImagemNota>(
        imgs[2],imgs[1]
    )

    var todasAbas = mutableListOf<AbaDeNotas>(
        AbaDeNotas(0,"todass", imgs),
        AbaDeNotas(0,"favoritas", imgsFavoritas)
    )







}