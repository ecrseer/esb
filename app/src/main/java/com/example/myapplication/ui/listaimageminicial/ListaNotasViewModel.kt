package com.example.myapplication.ui.listaimageminicial

import com.example.myapplication.domain.ImagemPesquisada
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.PersistenciaDadosNotas

class ListaNotasViewModel : ViewModel()  {

    private val _notasImgs = MutableLiveData<MutableList<ImagemPesquisada> >().apply {
        value = PersistenciaDadosNotas.imgs
    }
    val notasImgs: LiveData<MutableList<ImagemPesquisada> > = _notasImgs

    private val _posicaoAbaLista = MutableLiveData<Int>().apply {
        value=0
    }
    val posicaoAbaLista: LiveData<Int> = _posicaoAbaLista


    fun trocaAbaDaListaAtual(posicao:Int){
        val listaPretendida = PersistenciaDadosNotas.todasAbas[posicao]
        if(listaPretendida!=null){
            _posicaoAbaLista.postValue(posicao)
            _notasImgs.postValue(listaPretendida.lista)
        }
    }

    fun deletaNota(id:Int):Boolean{
        val todasNotas = _notasImgs?.value
        var deletouAlgo = false

        if(id==null) return deletouAlgo

        if (todasNotas!=null){
            todasNotas.apply { forEachIndexed { index, imagemPesquisada ->
                if(imagemPesquisada.id==id){
                    _notasImgs.value?.removeAt(index)
                    deletouAlgo=true
                    return@apply
                }

            } }
        }
        return deletouAlgo
    }
    fun criaNota(imagemPlaceholdr:String):Int?{
        var lista =_notasImgs?.value
        var novoId=0
        if(lista!=null && lista.size>0){
            novoId= lista?.last()?.id+1
        }

        val notaImgTemporaria = ImagemPesquisada(novoId,
            "$imagemPlaceholdr","","","")
        _notasImgs.value?.add(notaImgTemporaria)
        return _notasImgs.value?.size



    }
    private fun getNotasPesquisadas(txt:String):MutableList<ImagemPesquisada>{
        val results= mutableListOf<ImagemPesquisada>()
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


