package com.example.myapplication.view.nota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.MainViewModel
import com.example.myapplication.MainViewModelFactory
import com.example.myapplication.model.NoteImagens
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.model.ImagemPesquisada


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: FirstFragmentArgs by navArgs()
    private var posicao:Int=0
    private lateinit var mainViewModel: MainViewModel
    private var isNotaNovaDeveSerCriada = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        mainViewModel =
            ViewModelProvider(requireActivity(), MainViewModelFactory())[MainViewModel::class.java]

        arguments?.let {
            //notaImagemArmazenada = it.getInt("posicao")
        }
        isNotaNovaDeveSerCriada = args.posicao==null
        if(isNotaNovaDeveSerCriada){
            val notaImgTemporaria = ImagemPesquisada("","","","")
            mainViewModel.notasImgs.value?.add(notaImgTemporaria)
        }

        if(args!=null && args.posicao!=0){
            posicao = args.posicao
        }

        binding.pager.currentItem = posicao
        val adaptr = SliderAdapter(requireActivity(),true,mainViewModel.notasImgs.value?.size)
        binding.pager.adapter =  adaptr

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //retainInstance = true;
        val bin = binding.pager
        binding.pager.setCurrentItem(posicao,true);
        }


    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
}