package com.example.myapplication.api

import ImagemPesquisada
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
        call!!.enqueue(object : Callback<ImagemPesquisada?>{
            override fun onResponse(
                call: Call<ImagemPesquisada?>,
                response: Response<ImagemPesquisada?>
            ) {
                if(response.isSuccessful){
                    listenerImgService.obterImagemTerminou(response.body() )
                }
            }

            override fun onFailure(call: Call<ImagemPesquisada?>, t: Throwable) {
                listenerImgService.deuRuim("${t.message}")
            }

        })

    }
}