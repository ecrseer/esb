package com.example.myapplication.ui.tabs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.myapplication.domain.AbaDeNotas
import com.example.myapplication.services.db.AbaDeNotasRepository

class TabViewModel(application: Application): AndroidViewModel(application)  {
    private lateinit var abaDeNotasRepository: AbaDeNotasRepository
    lateinit var abasDeNotas: LiveData<List<AbaDeNotas>>;

    init {
        abaDeNotasRepository = AbaDeNotasRepository(application)
        abasDeNotas = abaDeNotasRepository
            .listarTodasAbasLiveData().asLiveData()
    }
    fun criaAba(abaDeNotas:AbaDeNotas?){
        var aba = abaDeNotas
        if(aba==null){
            aba = AbaDeNotas(0,"todas")
        }

        abaDeNotasRepository.criarAbaNova(aba)
    }
}