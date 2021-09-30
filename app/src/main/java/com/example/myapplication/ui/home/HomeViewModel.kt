package com.example.myapplication.ui.home

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Home fragment"
    }
    val text: LiveData<String> = _text

    private val _input = MutableLiveData<String>().apply {
        value = "bombom"
    }
    val input: LiveData<String> = _input

}