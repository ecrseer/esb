package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FirstFragmentViewModel::class.java)){

            return FirstFragmentViewModel() as T;
        }
        throw IllegalArgumentException("instanciando errado")
    }
}