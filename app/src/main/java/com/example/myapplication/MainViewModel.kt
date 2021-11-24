package com.example.myapplication

import com.example.myapplication.model.ImagemPesquisada
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



    private val _tituloNota = MutableLiveData<String>().apply {
        value = ""
    }
    val tituloNota: LiveData<String> = _tituloNota
    private val _fundoImagem = MutableLiveData<String>().apply {
        value = "https://i.imgur.com/1X1PZGq.png"
    }

    val fundoImagem: LiveData<String> = _fundoImagem




    fun pesquisaImagemOkHttp(palavrachave: String){
        val client = OkHttpClient()

        val request = Request.Builder().url(
            "$apiurl?palavraChave=$palavrachave").get().build()


        doAsync{
            val response = client.newCall(request).execute()
            var json = "nadinha"
            if(response.body()!==null) {
                json = response.body()!!.string();
                val imagemDaApi = Gson().fromJson(json, ImagemPesquisada::class.java)
                val imagemGrande = "${imagemDaApi.big}"
                val thumbP = "${imagemDaApi.thumb}"
                _fundoImagem.postValue(imagemGrande)
            }
        }



    }


    private val _notasImgs = MutableLiveData<MutableList<ImagemPesquisada> >().apply {
        value = NoteImagens.imgs
    }

    val notasImgs: LiveData<MutableList<ImagemPesquisada> > = _notasImgs

    fun carregaNotaSalva(posicao: Int){
        if(_notasImgs.value!=null){
            val imagemPesquisada = _notasImgs.value!![posicao]
            _fundoImagem.value = imagemPesquisada.big
            _tituloNota.value = imagemPesquisada.titulo
        }
    }

    val peneiraNotaPorTexto={txt:String->
        //todo
        if(txt.isBlank()){
            //_notasImgs.value=NoteImagens.imgs
        }else{
            val vll = _notasImgs.value
            _notasImgs.postValue(
                mutableListOf(
                    ImagemPesquisada("pg","nsei","mudei meu  bone","titulo2") )
                    )
            }

        }

    override fun obterImagemTerminou(imagem: ImagemPesquisada?) {
        if(imagem!=null){
            _fundoImagem.postValue(imagem.big)
        }
    }

    override fun deuRuim(erro: String) {
        println("deu ruim retrofit:\n$erro")
    }

    fun pesquisaImagemRetrofit(palavrachave:String){
        servico.obterImagem(palavrachave)
    }
}


