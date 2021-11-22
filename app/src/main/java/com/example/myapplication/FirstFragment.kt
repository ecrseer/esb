package com.example.myapplication

import ImagemPesquisada
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.databinding.FragmentFirstBinding
import com.squareup.picasso.Picasso


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        arguments?.let {
            //notaImagemArmazenada = it.getInt("posicao")
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pager.adapter = ScreenSlidePagerAdapter(requireActivity())
        //safeargs
        binding.pager.currentItem = intent.getIntExtra("posicao",0)
        //childFragmentManager
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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