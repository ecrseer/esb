package com.example.myapplication.services.db

import android.app.Application
import androidx.room.Room
//import com.example.myapplication.domain.AbaDeNotasWithImagemNotas
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking


class AbaDeNotasWithImagemNotasRepository(applicationContext: Application) {
 //   private lateinit var dao: AbaDeNotasWithImagemNotasDao

    /*init {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "db_imagemnota"
        ).build()

        dao = db.getAbaDeNotasWithImagemNotasDAO()
    }
    fun listaAbaDeNotasComImagemNotas(): Flow<List<AbaDeNotasWithImagemNotas>> {
        return runBlocking {
            return@runBlocking dao.getAbaDeNotasWithImagemNotas()
        }
    }*/
}