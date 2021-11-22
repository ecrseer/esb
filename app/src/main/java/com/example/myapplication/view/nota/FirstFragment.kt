package com.example.myapplication.view.nota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.model.NoteImagens
import com.example.myapplication.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: FirstFragmentArgs by navArgs()


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
        binding.pager.currentItem = args.posicao
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NoteImagens.imgs.size


        override fun createFragment(position: Int): Fragment {
            val frag = NotaFragment()

            val bundl = Bundle()
            bundl.putInt("posicao",position)
            frag.arguments = bundl

            return frag;
        }
    }
}