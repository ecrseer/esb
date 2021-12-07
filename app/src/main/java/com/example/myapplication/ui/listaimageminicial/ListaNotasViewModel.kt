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
    private lateinit var abaDeNotasWithImagemNotasRepository: AbaDeNotasWithImagemNotasRepository
    private lateinit var abaDeNotasRepository: AbaDeNotasRepository
    private lateinit var abaDeNotasImagemNotaRepository: AbaDeNotasImagemNotaRepository

    lateinit var notaImgsDoRoom:LiveData<List<ImagemNota>>;
    lateinit var abasDeNotas: LiveData<List<AbaDeNotas>>;
    lateinit var notaImgsAbaAtual:LiveData<List<AbaDeNotasWithImagemNotas>>;

    init {
        imageNotaRepository = ImagemNotaRepository(application)
        abaDeNotasWithImagemNotasRepository = AbaDeNotasWithImagemNotasRepository(application)
        abaDeNotasRepository = AbaDeNotasRepository(application)
        abaDeNotasImagemNotaRepository = AbaDeNotasImagemNotaRepository(application)


        notaImgsDoRoom = imageNotaRepository.listaImagemNotaLiveData().asLiveData()
        notaImgsAbaAtual = abaDeNotasWithImagemNotasRepository.listaAbaDeNotasComImagemNotas().asLiveData()
        abasDeNotas = abaDeNotasRepository.listarTodasAbasLiveData().asLiveData()
    }


    private val _notasImgs = MutableLiveData<MutableList<ImagemNota> >().apply{
        value = PersistenciaDadosNotas.imgs    }

    private val _posicaoAbaLista = MutableLiveData<Int>().apply {
        value=0
    }
    val posicaoAbaLista: LiveData<Int> = _posicaoAbaLista


    val abaAtual = MutableLiveData<AbaDeNotas>().apply {
        value= AbaDeNotas(0,"todas")
    }





    fun trocaAbaDaListaAtual(aba:AbaDeNotas){
       abaAtual.postValue(aba)
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
            val isLista = notaImgsDoRoom.value?.size?.equals(0)
            if(notaImgsDoRoom.value!=null ) {
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


