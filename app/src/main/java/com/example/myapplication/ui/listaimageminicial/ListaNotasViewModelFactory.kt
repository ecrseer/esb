package com.example.myapplication.ui.listaimageminicial

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ListaNotasViewModelFactory(private val application: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ListaNotasViewModel::class.java)){

            return ListaNotasViewModel(application) as T;
        }
        throw IllegalArgumentException("instanciando errado")
    }
}