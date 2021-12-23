package com.example.myapplication.ui.nota

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

//todo tentar com fragmentmanager e viewlife
class SliderAdapter (fa: FragmentManager,lifecycl:Lifecycle,
                     var tamanho:Int?) : FragmentStateAdapter(fa,lifecycl) {
    override fun getItemCount(): Int = tamanho?: 2

    private fun notaSegundoPosicao(position: Int):Fragment{
        val frag = NotaFragment()
        frag.arguments = bundleOf("posicaoNotaSelecionada" to position)
        return frag;
    }


    override fun createFragment(position: Int): Fragment {
        return notaSegundoPosicao(position);
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }




} 