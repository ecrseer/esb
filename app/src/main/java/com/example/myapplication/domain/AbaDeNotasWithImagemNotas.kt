package com.example.myapplication.domain

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/*
data class AbaDeNotasWithImagemNotas(
    @Embedded
    val abaDeNotas: AbaDeNotas,

    @Relation(
        parentColumn = "idAba",
        entity = ImagemNota::class,
        entityColumn = "idNota",
        associateBy = Junction(
            value = AbaDeNotasImagemNota::class,
            parentColumn = "idAba",
            entityColumn = "idNota"
        )
    )
    var listaDeNotas:List<ImagemNota>
) {

}*/
