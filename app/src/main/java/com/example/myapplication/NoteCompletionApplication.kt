package com.example.myapplication

import android.app.Application
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasRepository
import com.example.myapplication.services.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteCompletionApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob() )

    val database by lazy { AppDatabase.getDatabase(this,applicationScope)}
    val repository by lazy { AbaDeNotasWithImagemNotasRepository(database.getAbaDeNotasWithImagemNotasDAO() )}
}