package com.example.myapplication.ui.tabs

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.domain.*
import com.example.myapplication.services.db.AbaDeNotasRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasImagemNotaRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasRepository
import com.example.myapplication.services.db.ImagemNotaRepository
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class TabViewModel(abaDeNotasWithImagemNotasRepository: AbaDeNotasWithImagemNotasRepository): ViewModel()  {

    lateinit var todasImageNotasEabas:LiveData<List<AbaDeNotasWithImagemNotas>>;


    init {
        abaDeNotasRepository = AbaDeNotasRepository(application)
        abasDeNotas = abaDeNotasRepository
            .listarTodasAbasLiveData().asLiveData()

        abaDeNotasWithImagemNotasRepository = AbaDeNotasWithImagemNotasRepository(application)
        todasImageNotasEabas = abaDeNotasWithImagemNotasRepository.listaAbaDeNotasComImagemNotas()
            .asLiveData()

        imageNotaRepository = ImagemNotaRepository(application)
        todasNotas = imageNotaRepository.listaImagemNotaLiveData().asLiveData()

        abaDeNotasImagemNotaRepository = AbaDeNotasImagemNotaRepository(application)

    }

    var abaAtualComNotas= MutableLiveData<AbaDeNotasWithImagemNotas>()
     var tamanhoDaListaCriaRelacionamento= MutableLiveData<Int>().apply { value=0 }


    fun criaAbasIniciais(){
        if(abasDeNotas.value?.isNotEmpty()!=true){
            PersistenciaDadosNotas.abasIniciais.forEach {
                    aba->abaDeNotasRepository.criarAbaNova(aba)
            }
        }
    }
    fun criaAba(abaDeNotas:AbaDeNotas?){
        var aba = abaDeNotas
        if(aba==null){
            aba = AbaDeNotas(0,"todas")

        }
        abaDeNotasRepository.criarAbaNova(aba)
    }
     fun anexaNotaNaAba():Boolean{
            val aba= abaAtualComNotas.value?.abaDeNotas
            val ultimaNota = todasNotas?.value?.last()
            ultimaNota?.let {
                val imageNota:ImagemNota = it
                val relacao = AbaDeNotasImagemNota(imageNota.idNota,aba!!.idAba)
                abaDeNotasImagemNotaRepository.adicionarRelacaoAbaNota(relacao)
                return true
            }
         return false

    }
     fun criaNota(imagemPlaceholdr:String):Int? {
            val notaImgTemporaria = ImagemNota(0,
                "$imagemPlaceholdr","","","")
            imageNotaRepository.inserirAnotacao(notaImgTemporaria)
            return abaAtualComNotas.value?.listaDeNotas?.size

    }

}