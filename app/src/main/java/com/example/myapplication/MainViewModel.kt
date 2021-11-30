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

class MainViewModel : ViewModel()  {

    private val _notasImgs = MutableLiveData<MutableList<ImagemPesquisada> >().apply {
        value = NoteImagens.imgs
    }

    val notasImgs: LiveData<MutableList<ImagemPesquisada> > = _notasImgs

    val setNotaEditadaOuNova={posicao:Int,imagemP:ImagemPesquisada->
        _notasImgs.value?.set(posicao,imagemP)
    }
    fun deletaNota(id:Int):Boolean{
        val todasNotas = _notasImgs.value
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
        val ultimaNota=_notasImgs.value?.last()
        val ultimoIdNota = ultimaNota?.id
        if (ultimoIdNota!=null){
            val notaImgTemporaria = ImagemPesquisada(ultimoIdNota+1,
                "$imagemPlaceholdr","","","")
            _notasImgs.value?.add(notaImgTemporaria)
            return _notasImgs.value?.size
        }
        return null

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

    val peneiraNotaPorTexto={txt:String->
        if(txt.isBlank()){
            _notasImgs.postValue(NoteImagens.imgs)
        }else{
            if(_notasImgs.value!=null){
                val resultadoPesquisa=getNotasPesquisadas(txt)
                if(resultadoPesquisa.size>=1)
                    _notasImgs.postValue(resultadoPesquisa)

            }

            }
         false
        }


}


