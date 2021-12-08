package com.example.myapplication.ui.nota

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.services.ImagensService
import com.example.myapplication.services.ImagensServiceListener
import com.example.myapplication.domain.ImagemNota
import com.example.myapplication.services.db.ImagemNotaRepository
import kotlinx.coroutines.runBlocking

class NotaViewModel : ViewModel(), ImagensServiceListener {
    private val servico = ImagensService()

    //private lateinit var imageNotaRepository: ImagemNotaRepository
    init {
        //imageNotaRepository = ImagemNotaRepository(application)
        servico.setImagensServiceListener(this)
    }

    private val _isFavoritada = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isFavoritada: LiveData<Boolean> = _isFavoritada

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

    private val _notaImg = MutableLiveData<ImagemNota>()

    val notaImg: LiveData<ImagemNota> = _notaImg

    val carregando = MutableLiveData<Boolean>().apply { value = false }


    fun editaNotaAtual(cabecalho: String, conteudo: String, img: String) {
        if (_notaImg.value != null) {
            _notaImg.value?.apply {
                titulo = cabecalho
                texto = conteudo
                big = img
                thumb = img
            }
        }

    }
    fun deletaNota(): Boolean {
        var deletouAlgo = false

        //imageNotaRepository.removerAnotacao(_notaImg)
        deletouAlgo = true

        return deletouAlgo
    }

    fun carregaNotaRoom(listaNotaImgs: List<ImagemNota>, posicao: Int) {

        val imagemEncontrada = listaNotaImgs[posicao]
        if(imagemEncontrada==null){
            _tituloNota.value = "carregando"
        }else {
            _notaImg.postValue(imagemEncontrada)
            _fundoImagem.value = imagemEncontrada.big
            _tituloNota.value = imagemEncontrada.titulo
            _textoNota.value = imagemEncontrada.texto
        }
    }

    override fun obterImagemTerminou(imagem: ImagemNota?) {
        if (imagem != null) {
            _fundoImagem.postValue(imagem.big)
        }
    }

    override fun deuRuim(erro: String) {
        println("deu ruim retrofit:\n$erro")
    }

    fun pesquisaImagemRetrofit(palavrachave: String) {
        servico.obterImagem(palavrachave)
    }
}