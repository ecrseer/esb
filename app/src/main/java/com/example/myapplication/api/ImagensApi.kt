package com.example.myapplication.api

import com.example.myapplication.model.ImagemPesquisada
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagensApi {

    @GET("/pesquisaImagem")
    fun obterImagem(@Query("palavraChave") titulo:String):Call<ImagemPesquisada?>?

}