package com.example.myapplication.ui.listaimageminicial

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.domain.*
import com.example.myapplication.services.db.AbaDeNotasRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasImagemNotaRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasRepository
import com.example.myapplication.services.db.ImagemNotaRepository
import kotlinx.coroutines.*

class ListaNotasViewModel(): ViewModel()  {


    private val _listaImageNotas = MutableLiveData<MutableList<ImagemNota> >()
    val listaImagemNotas:LiveData<MutableList<ImagemNota> > = _listaImageNotas


      fun getListaNotasPesquisadas(txt:String?,listaNaDb:List<ImagemNota>):List<ImagemNota>{
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





}


