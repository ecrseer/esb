package com.example.myapplication.ui.listaimageminicial

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.domain.ImagemNota
import com.example.myapplication.domain.PersistenciaDadosNotas
import com.example.myapplication.services.ImagemNotaRepository

class ListaNotasViewModel(application: Application): AndroidViewModel(application)  {

    private lateinit var imageNotaRepository:ImagemNotaRepository


    lateinit var notaImgsDoRoom:LiveData<List<ImagemNota>>;

    private val _notasImgs = MutableLiveData<MutableList<ImagemNota> >().apply{
        value = PersistenciaDadosNotas.imgs    }
    val notasImgs: LiveData<MutableList<ImagemNota> > = _notasImgs


    init {
        imageNotaRepository = ImagemNotaRepository(application)
        notaImgsDoRoom = imageNotaRepository.listaImagemNotaLiveData().asLiveData()
    }


    private val _posicaoAbaLista = MutableLiveData<Int>().apply {
        value=0
    }
    val posicaoAbaLista: LiveData<Int> = _posicaoAbaLista

    //todo refatorar
    fun verificaSeNotaEhFavorita(id:Int): Boolean {
        val listaFavoritas = PersistenciaDadosNotas.todasAbas[1].lista
        var isNotaFavorita =false
        listaFavoritas.forEachIndexed { index, imagemPesquisada ->
            if(imagemPesquisada.id==id){
                isNotaFavorita= true
            }
        }
        return isNotaFavorita
    }

    fun trocaAbaDaListaAtual(posicao:Int){
        val listaPretendida = PersistenciaDadosNotas.todasAbas[posicao]
        if(listaPretendida!=null){
            _posicaoAbaLista.postValue(posicao)
            _notasImgs.postValue(listaPretendida.lista)
        }
    }
    fun editaNotaAtual(nota:ImagemNota){
        imageNotaRepository.editarAnotacao(nota)
    }

    fun deletaNota(id: Int): Boolean {
        var deletouAlgo = false

        if (id == null) return deletouAlgo

        notaImgsDoRoom?.value?.apply {
            forEachIndexed { index, imagemPesquisada ->
                if (imagemPesquisada.id == id) {
                    imageNotaRepository.removerAnotacao(imagemPesquisada)
                    deletouAlgo = true
                    return@apply
                }

            }
        }
        return deletouAlgo
    }
    fun criaNota(imagemPlaceholdr:String):Int?{
        var lista =_notasImgs?.value
        var novoId=0
        if(lista!=null && lista.size>0){
            novoId= lista?.last()?.id+1
        }


        val notaImgTemporaria = ImagemNota(0,
            "$imagemPlaceholdr","","","")

        imageNotaRepository.inserirAnotacao(notaImgTemporaria)
        return notaImgsDoRoom.value?.size



    }
    private fun getNotasPesquisadas(txt:String):MutableList<ImagemNota>{
        val results= mutableListOf<ImagemNota>()
        for(notaimg in _notasImgs.value!!){
            if (notaimg.titulo.contains(txt)){
                results.add(notaimg)
            }
        }
        return results;
    }



    fun peneiraNotaPorTexto(txt:String):Boolean{
        if(txt.isBlank()){
            _notasImgs.postValue(PersistenciaDadosNotas.imgs)
        }else{
            if(_notasImgs.value!=null){
               val resultadoPesquisa=getNotasPesquisadas(txt)
                if(resultadoPesquisa.size>=1) {
                    _notasImgs.postValue(resultadoPesquisa)
                    return true
                }

                    }

            }
         return false
        }



}


