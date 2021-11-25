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

    val adicionaNota={}

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


}


