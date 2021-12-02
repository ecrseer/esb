package com.example.myapplication.services

import com.example.myapplication.domain.ImagemPesquisada

interface ImagensServiceListener {
    fun obterImagemTerminou(imagem: ImagemPesquisada?)
    fun deuRuim(erro:String)
}