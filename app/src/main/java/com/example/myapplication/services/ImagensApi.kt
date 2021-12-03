package com.example.myapplication.services

import com.example.myapplication.domain.ImagemNota
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagensApi {

    @GET("/pesquisaImagem")
    fun obterImagem(@Query("palavraChave") titulo:String):Call<ImagemNota?>?

}