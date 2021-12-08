package com.example.myapplication.ui.tabs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.myapplication.domain.AbaDeNotas
import com.example.myapplication.domain.AbaDeNotasWithImagemNotas
import com.example.myapplication.services.db.AbaDeNotasRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TabViewModel(application: Application): AndroidViewModel(application)  {
    private lateinit var abaDeNotasRepository: AbaDeNotasRepository
    lateinit var abasDeNotas: LiveData<List<AbaDeNotas>>;


    private lateinit var abaDeNotasWithImagemNotasRepository: AbaDeNotasWithImagemNotasRepository
    lateinit var todasImageNotasEabas:LiveData<List<AbaDeNotasWithImagemNotas>>;

    var abaAtualComNotas= MutableLiveData<AbaDeNotasWithImagemNotas>()

    init {
        abaDeNotasRepository = AbaDeNotasRepository(application)
        abasDeNotas = abaDeNotasRepository
            .listarTodasAbasLiveData().asLiveData()

        abaDeNotasWithImagemNotasRepository = AbaDeNotasWithImagemNotasRepository(application)
        todasImageNotasEabas = abaDeNotasWithImagemNotasRepository.listaAbaDeNotasComImagemNotas()
            .asLiveData()
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

}