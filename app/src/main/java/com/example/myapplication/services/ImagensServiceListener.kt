package com.example.myapplication.services

import com.example.myapplication.domain.ImagemNota

interface ImagensServiceListener {
    fun obterImagemTerminou(imagem: ImagemNota?)
    fun deuRuim(erro:String)
}