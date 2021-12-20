package com.example.myapplication.services.db

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.myapplication.domain.AbaDeNotas
import com.example.myapplication.domain.ImagemNota
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class AbaDeNotasRepository(
    private var daoAbaDeNotas: AbaDeNotasDao,
    private var daoImagemNota: ImagemNotaDao,
    private val dao: AbaDeNotasWithImagemNotasDao
) {


    suspend fun criarAbaNova(abaDeNotas: AbaDeNotas) {
        return daoAbaDeNotas.adicionar(abaDeNotas)

    }

    fun listarTodasAbasLiveData(): Flow<List<AbaDeNotas>> {
        return daoAbaDeNotas.listar()

    }

    suspend fun obterPorId(id: Int): ImagemNota {
        return daoImagemNota.obter(id)

    }

    suspend fun listaImagemNotaLiveData(): Flow<List<ImagemNota>> {
        return daoImagemNota.listarLiveData()

    }

    suspend fun inserirAnotacao(nota: ImagemNota) {
        return daoImagemNota.inserir(nota)

    }

    suspend fun editarAnotacao(nota: ImagemNota) {
        return daoImagemNota.editar(nota)

    }

    suspend fun removerAnotacao(nota: ImagemNota) {
        return daoImagemNota.excluir(nota)

    }
}
