package com.example.myapplication

import com.example.myapplication.model.ImagemPesquisada
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.api.ImagensService
import com.example.myapplication.api.ImagensServiceListener
import com.example.myapplication.model.NoteImagens
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync

class MainViewModel : ViewModel() , ImagensServiceListener {
    private val servico = ImagensService()
    init{
        servico.setImagensServiceListener(this)
    }

    private val  apiurl = "https://notecompletion.herokuapp.com/pesquisaImagem"



    private val _input = MutableLiveData<String>().apply {
        value = ""
    }
    val input: LiveData<String> = _input
    private val _ldImagem = MutableLiveData<String>().apply {
        value = "https://i.imgur.com/1X1PZGq.png"
    }

    val ldImagem: LiveData<String> = _ldImagem


    fun carregaNotaSalva(imagemPesquisada: ImagemPesquisada){
        _ldImagem.value = imagemPesquisada.big
        _input.value = imagemPesquisada.titulo
    }

    fun pesquisaImagemDe(palavrachave: String){
        val client = OkHttpClient()

        val request = Request.Builder().url(
            "$apiurl?palavraChave=$palavrachave").get().build()


        doAsync{
            val response = client.newCall(request).execute()
            Log.d("HomeFragt","getand$response")
            var json = "nadinha"
            if(response.body()!==null) {
                json = response.body()!!.string();
                val imagemDaApi = Gson().fromJson(json, ImagemPesquisada::class.java)
                val imagemGrande = "${imagemDaApi.big}"
                val thumbP = "${imagemDaApi.thumb}"
                _ldImagem.postValue(imagemGrande)
            }


            Log.d("HomeFragt","ldImg eh ${_ldImagem.value}")

        }



    }


    private val _notasImgs = MutableLiveData<MutableList<ImagemPesquisada> >().apply {
        value = NoteImagens.imgs
    }

    val notasImgs: MutableLiveData<MutableList<ImagemPesquisada> > = _notasImgs

    val peneiraNotaPorTexto={txt:String->
        //todo
        if(txt.isBlank()){
            //_notasImgs.value=NoteImagens.imgs
        }else{
            val vll = notasImgs.value
            notasImgs.postValue(
                mutableListOf(
                    ImagemPesquisada("pg","nsei","mudei meu  bone","titulo2") )
                    )
            }

        }

    override fun obterImagemTerminou(imagem: ImagemPesquisada?) {
        if(imagem!=null){
            _ldImagem.postValue(imagem.big)
        }
    }

    override fun deuRuim(erro: String) {
        println("deu ruim retrofit:\n$erro")
    }
    fun pesquisaImagemRetrofit(palavrachave:String){
        servico.obterImagem(palavrachave)
    }
}


