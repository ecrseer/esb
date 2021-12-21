package com.example.myapplication.ui.tabs

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.services.db.AbaDeNotasRepository
import com.example.myapplication.services.db.AbaNotasRelacao.AbaDeNotasWithImagemNotasRepository
import com.example.myapplication.services.db.ImagemNotaRepository
import com.example.myapplication.ui.listaimageminicial.ListaNotasViewModel
import java.lang.IllegalArgumentException

class TabViewModelFactory (private val repository: AbaDeNotasRepository,
                private val imgNotaRepository:ImagemNotaRepository): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(TabViewModel::class.java)){

                return TabViewModel(repository,imgNotaRepository) as T;
            }
            throw IllegalArgumentException("TabViewModel instanciando errado")
        }

}