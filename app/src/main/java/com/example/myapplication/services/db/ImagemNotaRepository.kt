package com.example.myapplication.services.db

import android.app.Application
import androidx.room.Room
import com.example.myapplication.domain.ImagemNota
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking


class ImagemNotaRepository(
    private val dao: ImagemNotaDao
) {

    suspend fun obterPorId(id: Int): ImagemNota {
        return dao.obter(id)
    }

      fun listaImagemNotaLiveData(): Flow<List<ImagemNota>> {
        
        return dao.listarLiveData()

    }

    suspend fun listarImagemNota(): List<ImagemNota> {

        return dao.listar()

    }

    suspend fun inserirAnotacao(nota: ImagemNota):Long {

        return dao.inserir(nota)

    }

    suspend fun editarAnotacao(nota: ImagemNota) {

        return dao.editar(nota)

    }

    suspend fun removerAnotacao(nota: ImagemNota) {

        return dao.excluir(nota)

    }

}