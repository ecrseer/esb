package com.example.myapplication.api

import ImagemPesquisada

interface ImagensServiceListener {
    fun obterImagemTerminou(imagem:ImagemPesquisada?)
    fun deuRuim(erro:String)
}