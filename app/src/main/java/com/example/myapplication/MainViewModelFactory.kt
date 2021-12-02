package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.ui.listaimageminicial.ListaNotasViewModel
import java.lang.IllegalArgumentException

class MainViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ListaNotasViewModel::class.java)){

            return ListaNotasViewModel() as T;
        }
        throw IllegalArgumentException("instanciando errado")
    }
}