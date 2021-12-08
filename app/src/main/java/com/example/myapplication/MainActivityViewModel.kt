package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.myapplication.domain.*
import com.example.myapplication.services.db.AbaDeNotasRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasImagemNotaRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasRepository
import com.example.myapplication.services.db.ImagemNotaRepository
import kotlinx.coroutines.*

class MainActivityViewModel (application: Application): AndroidViewModel(application)  {

        private lateinit var abaDeNotasRepository: AbaDeNotasRepository
        lateinit var abasDeNotas: LiveData<List<AbaDeNotas>>;



        init {
            abaDeNotasRepository = AbaDeNotasRepository(application)
            abasDeNotas = abaDeNotasRepository
                .listarTodasAbasLiveData().asLiveData()

        }





}