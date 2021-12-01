package com.example.myapplication.ui.nota

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentNotaViewPagerBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NotaViewPagerFragment : Fragment() {

    private var _binding: FragmentNotaViewPagerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private var posicao:Int=0
    private var isNotaNovaDeveSerCriada = false
    val args: NotaViewPagerFragmentArgs by navArgs()

    private fun inicializaArgs(){
        if( args.posicao!=null){
            posicao = args.posicao
        }
        if(args.isNotaNova){/*
            isNotaNovaDeveSerCriada = args.isNotaNova
            val imagemPlaceholdr = getString(R.string.imagemTeste)
            val tamanhoDaLista = mainViewModel.criaNota(imagemPlaceholdr)
            if(tamanhoDaLista!=null)
                posicao = tamanhoDaLista-1*/
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotaViewPagerBinding.inflate(inflater, container, false)

        mainViewModel =
            ViewModelProvider(requireActivity(),
                MainViewModelFactory()).get(MainViewModel::class.java)

        if(args!=null){
             inicializaArgs()
        }

        val adaptr = SliderAdapter(childFragmentManager,lifecycle,
            mainViewModel.notasImgs.value?.size,false)

        with(binding.pager as ViewPager2){
            adapter =  adaptr
            currentItem = posicao
        }


        return binding.root

    }





    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().invalidateOptionsMenu()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

    }

}