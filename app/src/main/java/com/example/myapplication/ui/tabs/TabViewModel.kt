package com.example.myapplication.ui.tabs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.myapplication.domain.*
import com.example.myapplication.services.db.AbaDeNotasRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasImagemNotaRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasRepository
import com.example.myapplication.services.db.ImagemNotaRepository
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class TabViewModel(application: Application): AndroidViewModel(application)  {
    private lateinit var abaDeNotasRepository: AbaDeNotasRepository
    lateinit var abasDeNotas: LiveData<List<AbaDeNotas>>;


    private lateinit var abaDeNotasWithImagemNotasRepository: AbaDeNotasWithImagemNotasRepository
    lateinit var todasImageNotasEabas:LiveData<List<AbaDeNotasWithImagemNotas>>;

    private lateinit var imageNotaRepository: ImagemNotaRepository
    lateinit var todasNotas:LiveData<List<ImagemNota>>;

    private lateinit var abaDeNotasImagemNotaRepository: AbaDeNotasImagemNotaRepository

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