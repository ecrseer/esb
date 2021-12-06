package com.example.myapplication.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(tableName = "abadenotas")
data class AbaDeNotas constructor(
    @PrimaryKey(autoGenerate = true)
    var idAba:Int,

    @ColumnInfo(name = "nome")
    var nome:String


) {}