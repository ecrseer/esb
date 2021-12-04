package com.example.myapplication.services

import android.app.Application
import androidx.room.Room
import com.example.myapplication.domain.ImagemNota
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking


class ImagemNotaRepository(applicationContext:Application) {

    private lateinit var dao: ImagemNotaDao

    init {
        val db = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java, "db_imagemnota")
            .build()
        dao = db.getImagemNotaDAO()
    }
    fun obterPorId(id:Int):ImagemNota{

        return runBlocking {
             return@runBlocking dao.obter(id)
        }
    }
   fun listaImagemNotaLiveData(): Flow<List<ImagemNota>> {
        return runBlocking {
            return@runBlocking dao.listarLiveData()
        }
    }
    fun salvarAnotacao(nota:ImagemNota){
        return runBlocking {
            return@runBlocking dao.inserir(nota)
        }
    }

}