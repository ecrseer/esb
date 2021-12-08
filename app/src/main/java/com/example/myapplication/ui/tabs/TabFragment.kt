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
import com.example.myapplication.domain.AbaDeNotasWithImagemNotas
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

    private lateinit var tabLayout:TabLayout;
    private lateinit var viewpagr:ViewPager2;
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
    private fun carregaDadosAba(abaEnotas: AbaDeNotasWithImagemNotas?){
        var abasDeNotas = tabViewModel.abasDeNotas.value
        if (abasDeNotas?.isNotEmpty() == true) listaNotasViewModel.trocaAbaDaListaAtual(abaEnotas!!)
            else  tabViewModel.criaAba(null)

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
        tabLayout = binding.tabLayout
        viewpagr = binding.viewpager

        tabViewModel.todasImageNotasEabas.observe(viewLifecycleOwner, Observer {

        })

        tabViewModel.abasDeNotas.observe(viewLifecycleOwner, Observer {
            it.let{ lista->
                val qtdAbas = tabViewModel.abasDeNotas.value?.size?: 1
                viewpagr.adapter = TabAdapter(requireActivity(),qtdAbas)
                recriaTabMediator(tabLayout,viewpagr,lista)
            }
        })

        tabViewModel.todasNotas.observe(viewLifecycleOwner, Observer {
            listaNotasViewModel.renovaLista(it.toMutableList())
        })


    }

    fun mudaListaParaAbaEm(posicaoAtual:Int){
        val abaEnotasAtual = tabViewModel.todasImageNotasEabas.value!!.get(posicaoAtual)
        abaEnotasAtual?.abaDeNotas?.let {
            tabViewModel.abaAtualComNotas.postValue(abaEnotasAtual)
            if(abaEnotasAtual.listaDeNotas.isNotEmpty())
                listaNotasViewModel.trocaAbaDaListaAtual(abaEnotasAtual)
        }
    }
    override fun onResume() {
        super.onResume()
        tabViewModel.todasImageNotasEabas.value?.let {
            if(it.isEmpty()) carregaDadosAba(null)
            else carregaDadosAba(it.first())
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val posicaoAtual = tab?.position
                posicaoAtual?.let {
                    val existeRelacionamentoNotaAba = tabViewModel.todasImageNotasEabas?.value?.isNotEmpty()==true
                    if(existeRelacionamentoNotaAba){
                        mudaListaParaAbaEm(posicaoAtual)
                    }




                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {            }
            override fun onTabReselected(tab: TabLayout.Tab?) {            }

        })

        binding.fabAdicionaNota.setOnClickListener {
            val isNotaNova = true;
            val imagemPlaceholdr = getString(R.string.imagemTeste)


            GlobalScope.launch(Dispatchers.Main){
                val tamanhoDaLista = GlobalScope.async {
                    tabViewModel.criaNota(imagemPlaceholdr)
                }
                if(tamanhoDaLista.await()!=null){
                    val posicao=tamanhoDaLista.await()?.plus(1)!!
                    val action = TabFragmentDirections
                        .actionTabFragmentToNotaViewPagerFragment(
                            posicao, isNotaNova)
                    findNavController().navigate(action)
                }

            }

        }
    }

}