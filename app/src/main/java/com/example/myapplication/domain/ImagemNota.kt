package com.example.myapplication.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imagemnota")
data class ImagemNota constructor(

    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "big")
    var big: String,

    @ColumnInfo(name = "thumb")
    var thumb:String,

    @ColumnInfo(name = "texto")
    var texto: String,

    @ColumnInfo(name = "titulo")
    var titulo:String,

)
