package com.example.myapplication.services.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.domain.AbaDeNotas
import com.example.myapplication.domain.AbaDeNotasImagemNota
import com.example.myapplication.domain.ImagemNota
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasImagemNotaDao
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasDao
import kotlinx.coroutines.CoroutineScope

@Database(entities = [ImagemNota::class,AbaDeNotas::class,AbaDeNotasImagemNota::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getImagemNotaDAO(): ImagemNotaDao
    abstract fun getAbaDeNotasDAO(): AbaDeNotasDao
    abstract fun getAbaDeNotasWithImagemNotasDAO(): AbaDeNotasWithImagemNotasDao
    abstract fun getAbaDeNotasImagemNotasDAO(): AbaDeNotasImagemNotaDao

    companion object {
        //Singleton
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context:Context,scope: CoroutineScope){
            val instance = Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java,"db_imagemnota")
                .fallbackToDestructiveMigration()
                //.addCallback()
                .build()
            INSTANCE = instance;

            instance

        }
    }

}