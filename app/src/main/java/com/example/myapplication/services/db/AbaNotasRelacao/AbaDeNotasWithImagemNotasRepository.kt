package com.example.myapplication.services.db.AbaNotasRelacao

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.room.Room
import com.example.myapplication.domain.AbaDeNotasWithImagemNotas
import com.example.myapplication.services.db.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking


class AbaDeNotasWithImagemNotasRepository(
    private val dao: AbaDeNotasWithImagemNotasDao){

    fun listaAbaDeNotasComImagemNotas(): Flow<List<AbaDeNotasWithImagemNotas>> {
            return dao.getAbaDeNotasWithImagemNotas()
    }

}