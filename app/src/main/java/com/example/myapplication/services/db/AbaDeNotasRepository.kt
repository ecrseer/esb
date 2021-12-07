package com.example.myapplication.services.db

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.myapplication.domain.AbaDeNotas
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class AbaDeNotasRepository(applicationContext:Application) {
    private lateinit var dao: AbaDeNotasDao

    init {
        val db = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java, "db_imagemnota")
            .build()
        dao = db.getAbaDeNotasDAO()
    }

    fun criarAbaNova(abaDeNotas:AbaDeNotas){
        return runBlocking {
            return@runBlocking dao.adicionar(abaDeNotas)
        }
    }
    fun listarTodasAbasLiveData(): Flow<List<AbaDeNotas>> {
        return runBlocking {
            return@runBlocking dao.listar()
        }
    }
}
