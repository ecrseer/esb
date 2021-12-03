package com.example.myapplication.domain

data class AbaDeNotas constructor(
    var nome:String,
    var lista:MutableList<ImagemNota>
) {}