package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.databinding.ActivityModoLivroBinding

class ModoLivroActivity : AppCompatActivity() {

    private lateinit var binding:ActivityModoLivroBinding
    private var posicaoPagina=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModoLivroBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        binding.pager.adapter = ScreenSlidePagerAdapter(this)
        binding.pager.currentItem = intent.getIntExtra("posicao",0)
    }
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NoteImagens.imgs.size


        override fun createFragment(position: Int): Fragment {
            val frag = FirstFragment()

            val bundl = Bundle()
                bundl.putInt("posicao",position)
            frag.arguments = bundl

            return frag;
        }
    }
}