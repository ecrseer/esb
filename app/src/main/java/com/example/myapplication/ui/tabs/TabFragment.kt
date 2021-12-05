package com.example.myapplication.ui.tabs

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentTabBinding
import com.example.myapplication.domain.AbaDeNotas
import com.example.myapplication.domain.PersistenciaDadosNotas
import com.example.myapplication.ui.listaimageminicial.ListaNotasViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


/**
 * A simple [Fragment] subclass.
 * Use the [TabFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabFragment : Fragment() {
    private var _binding: FragmentTabBinding?=null
    private val binding get()= _binding!!

    private var param1: String? = null
    private val listaDeAbas:MutableList<AbaDeNotas> = PersistenciaDadosNotas.todasAbas
    private lateinit var listaNotasViewModel: ListaNotasViewModel

    private val listenerMudarListaAba = object : TabLayout.OnTabSelectedListener{
        override fun onTabSelected(tab: TabLayout.Tab?) {
            val posicaoAtual=tab?.position
            if(posicaoAtual!=null){
                listaNotasViewModel.trocaAbaDaListaAtual(posicaoAtual)
            }
        }
        override fun onTabUnselected(tab: TabLayout.Tab?) {            }
        override fun onTabReselected(tab: TabLayout.Tab?) {            }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString("ARG_PARAM1")
        }

        listaNotasViewModel =
            ViewModelProvider(requireActivity(), MainViewModelFactory(requireActivity().application))
                .get(ListaNotasViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.tabLayout
        val viewpagr = binding.viewpager

        viewpagr.adapter = TabAdapter(requireActivity(),listaDeAbas.size)

        TabLayoutMediator(tabLayout, viewpagr){
                tab,position->
            viewpagr.setCurrentItem(tab.position,true)
            tab.text = "${listaDeAbas[position].nome}"
        }.attach()

        tabLayout.addOnTabSelectedListener(listenerMudarListaAba)



    }

}