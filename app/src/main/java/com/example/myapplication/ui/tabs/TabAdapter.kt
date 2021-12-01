package com.example.myapplication.ui.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.ui.listaimageminicial.ListaImagemPesquisadaFragment
import com.example.myapplication.ui.sobre.SobreFragment

class TabAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        lateinit var frag:Fragment;
        when(position){
            1 -> frag= SobreFragment()
            0 -> frag= ListaImagemPesquisadaFragment()
        }
        return frag;
    }
}