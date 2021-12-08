package com.example.myapplication.ui.listaimageminicial

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.domain.*
import com.example.myapplication.services.db.AbaDeNotasRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasImagemNotaRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasRepository
import com.example.myapplication.services.db.ImagemNotaRepository
import kotlinx.coroutines.*

class ListaNotasViewModel(application: Application): AndroidViewModel(application)  {

    private lateinit var imageNotaRepository: ImagemNotaRepository
    private lateinit var abaDeNotasImagemNotaRepository: AbaDeNotasImagemNotaRepository

    lateinit var notaImgsDoRoom:LiveData<List<ImagemNota>>;

    init {
        imageNotaRepository = ImagemNotaRepository(application)
        abaDeNotasImagemNotaRepository = AbaDeNotasImagemNotaRepository(application)
    }


    private val _listaImageNotas = MutableLiveData<MutableList<ImagemNota> >()
    val listaImagemNotas:LiveData<MutableList<ImagemNota> > = _listaImageNotas

    val abaAtual = MutableLiveData<AbaDeNotas>().apply {
        value= AbaDeNotas(0,"todas")
    }






    fun trocaAbaDaListaAtual(listaAbaENotas:AbaDeNotasWithImagemNotas){
        abaAtual.postValue(listaAbaENotas.abaDeNotas)
       _listaImageNotas.postValue(listaAbaENotas.listaDeNotas.toMutableList())
    }
    fun editaNotaAtual(nota:ImagemNota){
        imageNotaRepository.editarAnotacao(nota)
    }

    fun deletaNota(id: Int): Boolean {
        var deletouAlgo = false

        if (id == null) return deletouAlgo

        notaImgsDoRoom?.value?.apply {
            forEachIndexed { index, imagemPesquisada ->
                if (imagemPesquisada.idNota == id) {
                    imageNotaRepository.removerAnotacao(imagemPesquisada)
                    deletouAlgo = true
                    return@apply
                }

            }
        }
        return deletouAlgo
    }
    suspend fun criaNota(imagemPlaceholdr:String):Int? {

        var aba = abaAtual.value
        if(aba==null) aba = AbaDeNotas(0,"todas")

        val notaImgTemporaria = ImagemNota(0,
            "$imagemPlaceholdr","","","")

        GlobalScope.launch(Dispatchers.Default){
            delay(3800)

            if(notaImgsDoRoom.value?.last()!=null ) {
                val imageNota:ImagemNota = notaImgsDoRoom.value!!.last()
                val relacao = AbaDeNotasImagemNota(imageNota.idNota,aba.idAba)
                abaDeNotasImagemNotaRepository.adicionarRelacaoAbaNota(relacao)
            }
        }
        return withContext(Dispatchers.Main){
            imageNotaRepository.inserirAnotacao(notaImgTemporaria)
             delay(700)
             notaImgsDoRoom.value?.size
        }

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





}


