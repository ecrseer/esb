package com.example.myapplication.view.nota

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.api.ImagensService
import com.example.myapplication.api.ImagensServiceListener
import com.example.myapplication.model.ImagemPesquisada

class NotaViewModel: ViewModel(), ImagensServiceListener {
    private val servico = ImagensService()
    init{
        servico.setImagensServiceListener(this)
    }

    private val _tituloNota = MutableLiveData<String>().apply {
        value = ""
    }
    val tituloNota: LiveData<String> = _tituloNota
    private val _textoNota = MutableLiveData<String>().apply {
        value = ""
    }
    val textoNota: LiveData<String> = _textoNota


    private val _fundoImagem = MutableLiveData<String>().apply {
        value = "https://i.imgur.com/1X1PZGq.png"
    }

    val fundoImagem: LiveData<String> = _fundoImagem


    fun carregaNotaSalva(listaNotaImgs:MutableList<ImagemPesquisada>, posicao: Int){
            val imagemPesquisada = listaNotaImgs[posicao]
            _fundoImagem.value = imagemPesquisada.big
            _tituloNota.value = imagemPesquisada.titulo
            _textoNota.value = imagemPesquisada.texto

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