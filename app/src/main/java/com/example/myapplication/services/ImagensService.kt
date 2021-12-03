package com.example.myapplication.services

import com.example.myapplication.domain.ImagemNota
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ImagensService {
    private lateinit var api: ImagensApi
    private lateinit var listenerImgService:ImagensServiceListener

    init{
        val retr = Retrofit.Builder()
            .baseUrl("https://notecompletion.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

     api = retr.create(ImagensApi::class.java)

    }
    fun setImagensServiceListener(listener:ImagensServiceListener){
         listenerImgService = listener
    }
    fun obterImagem(titulo:String){
        val call = api.obterImagem(titulo)
        call!!.enqueue(object : Callback<ImagemNota?>{
            override fun onResponse(
                call: Call<ImagemNota?>,
                response: Response<ImagemNota?>
            ) {
                if(response.isSuccessful){
                    listenerImgService.obterImagemTerminou(response.body() )
                }
            }

            override fun onFailure(call: Call<ImagemNota?>, t: Throwable) {
                listenerImgService.deuRuim("${t.message}")
            }

        })

    }
}