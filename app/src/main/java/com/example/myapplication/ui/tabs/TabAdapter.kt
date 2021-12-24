package com.example.myapplication.ui.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.ui.listaimageminicial.ListaImagemPesquisadaFragment
import com.example.myapplication.ui.sobre.SobreFragment

class TabAdapter(fa: FragmentManager,lifecycl: Lifecycle,var tamanho:Int) : FragmentStateAdapter(fa,lifecycl) {
    override fun getItemCount(): Int = tamanho

    override fun createFragment(position: Int): Fragment {

        return ListaImagemPesquisadaFragment();
    }
}