package com.example.myapplication

import android.app.Application
import com.example.myapplication.services.db.AbaDeNotasRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasRepository
import com.example.myapplication.services.db.AppDatabase
import com.example.myapplication.services.db.ImagemNotaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteCompletionApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob() )

    val database by lazy { AppDatabase.getDatabase(this,applicationScope)}
    val abaNotasRepository by lazy { AbaDeNotasRepository(
        database.getAbaDeNotasDAO(),
        database.getAbaDeNotasWithImagemNotasDAO(),
        database.getAbaDeNotasImagemNotasDAO(),
        database.getImagemNotaDAO()
    ) }
    val imgNotasRepository by lazy {
        ImagemNotaRepository(database.getImagemNotaDAO() )
    }
}