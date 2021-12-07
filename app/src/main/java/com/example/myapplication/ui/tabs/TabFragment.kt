package com.example.myapplication.ui.tabs

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentTabBinding
import com.example.myapplication.domain.AbaDeNotas
import com.example.myapplication.domain.PersistenciaDadosNotas
import com.example.myapplication.ui.listaimageminicial.ListaNotasViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


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
    private lateinit var tabViewModel:TabViewModel

    private lateinit var tabLayoutMediator: TabLayoutMediator;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString("ARG_PARAM1")
        }

        listaNotasViewModel =
            ViewModelProvider(requireActivity(), MainViewModelFactory(requireActivity().application))
                .get(ListaNotasViewModel::class.java)
        tabViewModel = ViewModelProvider(this)
            .get(TabViewModel::class.java)

    }
    private fun carregaDadosAba(abasDeNotas: List<AbaDeNotas>){
        if(abasDeNotas.size==0)  tabViewModel.criaAba(null)
        else listaNotasViewModel.abaAtual.postValue(abasDeNotas.first())

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabBinding.inflate(inflater,container,false)

        // Inflate the layout for this fragment
        return binding.root
    }
    private fun recriaTabMediator(tabLayout: TabLayout, viewpagr:ViewPager2,
                                  listaAbas:List<AbaDeNotas>){

        tabLayoutMediator = TabLayoutMediator(tabLayout, viewpagr){
                tab,position->
            /*if(::tabLayoutMediator.isInitialized)
                tabLayoutMediator?.detach()*/

            viewpagr.setCurrentItem(tab.position,true)
            tab.text = listaAbas[position].nome
        }

        tabLayoutMediator.attach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.tabLayout
        val viewpagr = binding.viewpager

        tabViewModel.abasDeNotas.observe(viewLifecycleOwner, Observer {
            val qtdAbas = tabViewModel.abasDeNotas.value?.size?: 1
            carregaDadosAba(it)
            viewpagr.adapter = TabAdapter(requireActivity(),qtdAbas)
            recriaTabMediator(tabLayout,viewpagr,it)

        })
        listaNotasViewModel.notaImgsDoRoom.observe(viewLifecycleOwner, Observer {
            val listaDeAbas = tabViewModel.abasDeNotas.value
            if(listaDeAbas!=null)
                recriaTabMediator(tabLayout,viewpagr,listaDeAbas)
        })



        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val posicaoAtual=tab?.position
                val abaAtual = tabViewModel.abaNaPosicao(posicaoAtual)
                if(abaAtual!=null){
                    listaNotasViewModel.trocaAbaDaListaAtual(abaAtual)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {            }
            override fun onTabReselected(tab: TabLayout.Tab?) {            }

        })



    }

    override fun onResume() {
        super.onResume()
        binding.fabAdicionaNota.setOnClickListener {
            val isNotaNova = true;
            val imagemPlaceholdr = getString(R.string.imagemTeste)


            GlobalScope.launch(Dispatchers.Main){
                val tamanhoDaLista = GlobalScope.async {
                    listaNotasViewModel.criaNota(imagemPlaceholdr)
                }
                if(tamanhoDaLista.await()!=null){

                    val action = TabFragmentDirections
                        .actionTabFragmentToNotaViewPagerFragment(tamanhoDaLista
                            .await()?.plus(1)!!, isNotaNova)
                    findNavController().navigate(action)
                }

            }

        }
    }

}