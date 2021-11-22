package com.example.myapplication.api

import com.example.myapplication.model.ImagemPesquisada

interface ImagensServiceListener {
    fun obterImagemTerminou(imagem: ImagemPesquisada?)
    fun deuRuim(erro:String)
}