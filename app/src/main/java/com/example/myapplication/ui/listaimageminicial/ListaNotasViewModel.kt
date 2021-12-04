package com.example.myapplication.ui.listaimageminicial

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.domain.ImagemNota
import com.example.myapplication.domain.PersistenciaDadosNotas
import com.example.myapplication.services.ImagemNotaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ListaNotasViewModel(application: Application): AndroidViewModel(application)  {

    private lateinit var imageNotaRepository:ImagemNotaRepository


    lateinit var notaImgsDoRoom:LiveData<List<ImagemNota>>;

    private val _notasImgs = MutableLiveData<MutableList<ImagemNota> >().apply{
        value = PersistenciaDadosNotas.imgs    }


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
    suspend fun criaNota(imagemPlaceholdr:String):Int? {

        val notaImgTemporaria = ImagemNota(0,
            "$imagemPlaceholdr","","","")
        return withContext(Dispatchers.Main){
            imageNotaRepository.inserirAnotacao(notaImgTemporaria)
             delay(1000)
             notaImgsDoRoom.value?.size
        }

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
      fun getListaNotasPesquisadas(txt:String?):List<ImagemNota>{
        var listaNaDb =  notaImgsDoRoom.value
        val results= mutableListOf<ImagemNota>()
          if(txt==null) return results

          if (listaNaDb != null) {
              for(notaimg in listaNaDb){
                  if (notaimg.titulo.contains(txt)){
                      results.add(notaimg)
                  }
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


