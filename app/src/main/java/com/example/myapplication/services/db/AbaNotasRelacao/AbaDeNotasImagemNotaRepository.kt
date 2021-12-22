package com.example.myapplication.services.db.AbaNotasRelacao

import android.app.Application
import androidx.room.Room
import com.example.myapplication.domain.AbaDeNotasImagemNota
import com.example.myapplication.services.db.AppDatabase
import kotlinx.coroutines.runBlocking

class AbaDeNotasImagemNotaRepository(applicationContext:Application) {
    lateinit var dao: AbaDeNotasImagemNotaDao
    init {
        val db = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java, "db_imagemnota")
            .build()
        dao = db.getAbaDeNotasImagemNotasDAO()
    }

}