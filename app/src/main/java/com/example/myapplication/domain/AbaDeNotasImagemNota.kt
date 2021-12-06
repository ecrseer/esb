package com.example.myapplication.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["idNota","idAba"])
data class AbaDeNotasImagemNota(
    val idNota: Int,
    val idAba:Int
) {

}
