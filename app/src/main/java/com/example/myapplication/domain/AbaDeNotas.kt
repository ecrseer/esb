package com.example.myapplication.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "abadenotas")
data class AbaDeNotas constructor(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "nome")
    var nome:String

) {}