package com.example.myapplication.domain

import androidx.room.Entity

@Entity(primaryKeys = ["idNota","idAba"])
data class AbaDeNotasImagemNota(
    val idNota: Long,
    val idAba:Int
) {

}
