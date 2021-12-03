package com.example.myapplication.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.domain.ImagemNota

@Database(entities = [ImagemNota::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getImagemNotaDAO():ImagemNotaDao

}