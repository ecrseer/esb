package com.example.myapplication.ui.nota

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.services.db.AbaDeNotasRepository
import com.example.myapplication.services.db.ImagemNotaRepository
import com.example.myapplication.ui.tabs.TabViewModel
import java.lang.IllegalArgumentException

class NotaViewModelFactory (private val repository: ImagemNotaRepository
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(NotaViewModel::class.java)){

                return NotaViewModel(repository) as T;
            }
            throw IllegalArgumentException("TabViewModel instanciando errado")
        }

    }
