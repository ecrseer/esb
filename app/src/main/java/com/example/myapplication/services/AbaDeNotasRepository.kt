package com.example.myapplication.services

import android.app.Application
import androidx.room.Room

class AbaDeNotasRepository(applicationContext:Application) {
    private lateinit var dao: AbaDeNotasDao

    init {
        val db = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java, "db_imagemnota")
            .build()
        dao = db.getAbaDeNotasDAO()
    }
}