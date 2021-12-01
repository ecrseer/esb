package com.example.myapplication.ui.nota

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

//todo tentar com fragmentmanager e viewlife
class SliderAdapter (fa: FragmentManager,lifecycl:Lifecycle,
                     var tamanho:Int?,var isItemVisivel:Boolean) : FragmentStateAdapter(fa,lifecycl) {
    override fun getItemCount(): Int = tamanho?: 2
    /*override fun getCount(): Int {
        return  tamanho?: 2;
    }*/
    private fun notaSegundoPosicao(position: Int):Fragment{
        val frag = NotaFragment()
        val bundl = Bundle()
            bundl.putInt("posicao",position)

            bundl.putBoolean("isItemVisivel",isItemVisivel)
        frag.arguments = bundl

        return frag;
    }


    override fun createFragment(position: Int): Fragment {
        return notaSegundoPosicao(position);
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }




} 