package com.example.myapplication.view.nota

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class SliderAdapter (fa: FragmentActivity, val isNotaNova: Boolean,var tamanho:Int?) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = tamanho?: 2
    /*override fun getCount(): Int {
        return  tamanho?: 2;
    }*/
    private fun notaSegundoPosicao(position: Int):Fragment{
        val frag = NotaFragment()
        val bundl = Bundle()

        if(isNotaNova){
            //todo
        }
        bundl.putInt("posicao",position)
        frag.arguments = bundl

        return frag;
    }

    /*override fun getItem(position: Int): Fragment {
        return notaSegundoPosicao(position);
    }*/
    override fun createFragment(position: Int): Fragment {
        return notaSegundoPosicao(position);
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }




} 