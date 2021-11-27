package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

open class FragmentComMenu: Fragment(){
    init{
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //menu.clear();

    }
}