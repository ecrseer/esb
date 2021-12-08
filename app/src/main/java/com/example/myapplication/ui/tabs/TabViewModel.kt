package com.example.myapplication.ui.tabs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.myapplication.domain.AbaDeNotas
import com.example.myapplication.domain.AbaDeNotasImagemNota
import com.example.myapplication.domain.AbaDeNotasWithImagemNotas
import com.example.myapplication.domain.ImagemNota
import com.example.myapplication.services.db.AbaDeNotasRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasImagemNotaRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasRepository
import com.example.myapplication.services.db.ImagemNotaRepository
import kotlinx.coroutines.*

class TabViewModel(application: Application): AndroidViewModel(application)  {
    private lateinit var abaDeNotasRepository: AbaDeNotasRepository
    lateinit var abasDeNotas: LiveData<List<AbaDeNotas>>;


    private lateinit var abaDeNotasWithImagemNotasRepository: AbaDeNotasWithImagemNotasRepository
    lateinit var todasImageNotasEabas:LiveData<List<AbaDeNotasWithImagemNotas>>;

    private lateinit var todasNotasRepository: ImagemNotaRepository
    lateinit var todasNotas:LiveData<List<ImagemNota>>;

    private lateinit var abaDeNotasImagemNotaRepository: AbaDeNotasImagemNotaRepository


    private lateinit var imageNotaRepository: ImagemNotaRepository

    var abaAtualComNotas= MutableLiveData<AbaDeNotasWithImagemNotas>()

    init {
        abaDeNotasRepository = AbaDeNotasRepository(application)
        abasDeNotas = abaDeNotasRepository
            .listarTodasAbasLiveData().asLiveData()

        abaDeNotasWithImagemNotasRepository = AbaDeNotasWithImagemNotasRepository(application)
        todasImageNotasEabas = abaDeNotasWithImagemNotasRepository.listaAbaDeNotasComImagemNotas()
            .asLiveData()

        todasNotasRepository = ImagemNotaRepository(application)
        todasNotas = todasNotasRepository.listaImagemNotaLiveData().asLiveData()

        abaDeNotasImagemNotaRepository = AbaDeNotasImagemNotaRepository(application)
        imageNotaRepository = ImagemNotaRepository(application)
    }
    fun carregaPrimeiraAba(){
        if(todasImageNotasEabas!=null){
            abaAtualComNotas.apply {
                value=todasImageNotasEabas.value?.first()
            }
        }
    }
    fun criaAbasIniciais(){
        val aba = AbaDeNotas(0,"favoritos")
        abaDeNotasRepository.criarAbaNova(aba)

        GlobalScope.launch(Dispatchers.Default){
            delay(500)
            val abaMais = AbaDeNotas(0,"+")
            abaDeNotasRepository.criarAbaNova(abaMais)
        }

    }
    fun criaAba(abaDeNotas:AbaDeNotas?){
        var aba = abaDeNotas
        if(aba==null){
            aba = AbaDeNotas(0,"todas")
            criaAbasIniciais()
        }
        abaDeNotasRepository.criarAbaNova(aba)
    }
    fun abaNaPosicao(posicao:Int?): AbaDeNotas? {
        return if(posicao!=null) abasDeNotas.value?.get(posicao)
        else null
    }
    suspend fun anexaNotaNaAba(aba:AbaDeNotas){
        GlobalScope.launch(Dispatchers.Default){
            delay(3800)
            todasNotas.value?.last()?.let {
                val imageNota:ImagemNota = it
                val relacao = AbaDeNotasImagemNota(imageNota.idNota,aba.idAba)
                abaDeNotasImagemNotaRepository.adicionarRelacaoAbaNota(relacao)
            }
        }
    }
    suspend fun criaNota(imagemPlaceholdr:String):Int? {

        var aba = abaAtualComNotas.value?.abaDeNotas
        if(aba==null) aba = AbaDeNotas(0,"todas")

        val notaImgTemporaria = ImagemNota(0,
            "$imagemPlaceholdr","","","")

        anexaNotaNaAba(aba)
        return withContext(Dispatchers.Main){
            imageNotaRepository.inserirAnotacao(notaImgTemporaria)
            delay(700)
            abaAtualComNotas.value?.listaDeNotas?.size
        }

    }

}