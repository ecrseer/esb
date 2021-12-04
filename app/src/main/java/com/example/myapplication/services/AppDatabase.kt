package com.example.myapplication.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.domain.AbaDeNotas
import com.example.myapplication.domain.ImagemNota

@Database(entities = [ImagemNota::class,AbaDeNotas::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getImagemNotaDAO():ImagemNotaDao
    abstract fun getAbaDeNotasDAO():AbaDeNotasDao

}