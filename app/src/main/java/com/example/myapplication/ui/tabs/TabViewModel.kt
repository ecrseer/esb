package com.example.myapplication.ui.tabs

import androidx.lifecycle.*
import com.example.myapplication.domain.*
import com.example.myapplication.services.db.AbaDeNotasRepository
import com.example.myapplication.services.db.ImagemNotaRepository
import kotlinx.coroutines.*

class TabViewModel(private val abaDeNotasRepository: AbaDeNotasRepository,
                        private val imageNotaRepository: ImagemNotaRepository): ViewModel()  {

      val todasImageNotasEabas:LiveData<List<AbaDeNotasWithImagemNotas>>
            = abaDeNotasRepository.listaAbaDeNotasComImagemNotas().asLiveData();
    val todasAbas:LiveData<List<AbaDeNotas>> = abaDeNotasRepository.listarTodasAbasLiveData().asLiveData()
    val todasNotas:LiveData<List<ImagemNota>> = imageNotaRepository.listaImagemNotaLiveData().asLiveData()



    var abaAtualComNotas= MutableLiveData<AbaDeNotasWithImagemNotas>().apply {
        value = todasImageNotasEabas.value?.get(0)
    }
    val temNotaNova = MutableLiveData<Boolean>().apply { value=false }
    val idNotaNova = MutableLiveData<Long>().apply { value=0 }
    val posicaoAbaAtual = MutableLiveData<Int>().apply { value=0 }
     var tamanhoDaListaCriaRelacionamento= MutableLiveData<Int>().apply { value=0 }

    val abaAtualById = abaDeNotasRepository
        .abaDeNotasComImagemNotasByIdAba(posicaoAbaAtual.value!!)


    fun mudaListaParaAbaEm(posicaoAtual:Int){
        val existeRelacionamentoNotaAba =
            todasImageNotasEabas?.value?.isNotEmpty() == true
        if (existeRelacionamentoNotaAba) {
            posicaoAbaAtual.postValue(posicaoAtual)
            atualizaAbaAtual()
        }

    }
    fun atualizaAbaAtual(){
        todasImageNotasEabas.value.let {
            val posicao = abaAtualComNotas.value?.abaDeNotas?.idAba?.minus(1)?: 0
            val abaEnotasAtual = it?.get(posicao)
            abaAtualComNotas.postValue(abaEnotasAtual)

        }
    }
    fun criaAbasIniciais(){
        if(todasImageNotasEabas.value?.isNotEmpty()!=true){
            PersistenciaDadosNotas.abasIniciais.forEach {
                    aba->
                viewModelScope.launch {
                    abaDeNotasRepository.criarAbaNova(aba)
                }
            }
        }
    }

    fun criaAba(abaDeNotas:AbaDeNotas?){
        var aba = abaDeNotas
        if(aba==null){
            aba = AbaDeNotas(0,"todas")
        }
        viewModelScope.launch {
            abaDeNotasRepository.criarAbaNova(aba)
        }
    }

     fun criaNota(imagemPlaceholdr:String):Int? {
            val notaImgTemporaria = ImagemNota(0,
                "$imagemPlaceholdr","","","")

            viewModelScope.launch {
                abaAtualComNotas.value.let {
                    if(it?.abaDeNotas?.idAba!=null){
                        idNotaNova.postValue(
                            abaDeNotasRepository.criarNotaNova(it.abaDeNotas.idAba,notaImgTemporaria)
                        )

                        tamanhoDaListaCriaRelacionamento.postValue(todasNotas.value?.size)
                        temNotaNova.postValue(true)
                    }

                }
            }
            return abaAtualComNotas.value?.listaDeNotas?.size

    }
    inner class NotaViewM(){

    }

}