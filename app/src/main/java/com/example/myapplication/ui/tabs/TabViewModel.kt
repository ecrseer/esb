package com.example.myapplication.ui.tabs

import androidx.lifecycle.*
import com.example.myapplication.domain.*
import com.example.myapplication.services.db.AbaDeNotasRepository
import com.example.myapplication.services.db.ImagemNotaRepository
import kotlinx.coroutines.*

class TabViewModel(
    private val abaDeNotasRepository: AbaDeNotasRepository,
    private val imageNotaRepository: ImagemNotaRepository
) : ViewModel() {

    val todasImageNotasEabas: LiveData<List<AbaDeNotasWithImagemNotas>> =
        abaDeNotasRepository.listaAbaDeNotasComImagemNotas().asLiveData();
    val todasAbas: LiveData<List<AbaDeNotas>> =
        abaDeNotasRepository.listarTodasAbasLiveData().asLiveData()


    val temNotaNova = MutableLiveData<Boolean>().apply { value = false }
    val idNotaNova = MutableLiveData<Long>().apply { value = 0 }
    val posicaoAbaAtual = MutableLiveData<Int>().apply { value = 0 }

    var listaAtualById = Transformations.switchMap(posicaoAbaAtual) { posi ->
        abaDeNotasRepository
            .listaDaAbaByIdAba(posi.plus(1))
    }

    fun mudaListaParaAbaEm(posicaoAtual: Int) {
        val existeRelacionamentoNotaAba =
            todasImageNotasEabas?.value?.isNotEmpty() == true
        if (existeRelacionamentoNotaAba) {
            posicaoAbaAtual.postValue(posicaoAtual)
        }
    }

    fun criaAbasIniciais() {
        if (todasImageNotasEabas.value?.isNotEmpty() != true) {
            PersistenciaDadosNotas.abasIniciais.forEach { aba ->
                viewModelScope.launch {
                    abaDeNotasRepository.criarAbaNova(aba)
                }
            }
        }
    }

    fun criaAba(abaDeNotas: AbaDeNotas?) {
        var aba = abaDeNotas
        if (aba == null) {
            aba = AbaDeNotas(0, "todas")
        }
        viewModelScope.launch {
            abaDeNotasRepository.criarAbaNova(aba)
        }
    }

    fun criaNota(imagemPlaceholdr: String) {
        val notaImgTemporaria = ImagemNota(
            0,
            "$imagemPlaceholdr", "", "", ""
        )

        viewModelScope.launch {
            posicaoAbaAtual.value.let {
                if(it!=null){
                    temNotaNova.postValue(true)
                    idNotaNova.postValue(
                        abaDeNotasRepository.criarNotaNova(it.plus(1), notaImgTemporaria)
                    )
                }

            }


        }

    }


}