package com.example.myapplication.ui.tabs

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentTabBinding
import com.example.myapplication.domain.AbaDeNotas
import com.example.myapplication.domain.AbaDeNotasWithImagemNotas
import com.example.myapplication.domain.PersistenciaDadosNotas
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*


/**
 * A simple [Fragment] subclass.
 * Use the [TabFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabFragment : Fragment() {
    private var _binding: FragmentTabBinding?=null
    private val binding get()= _binding!!

    private var param1: String? = null

    private val tabViewModel:TabViewModel by activityViewModels {
        TabViewModelFactory(
            (requireActivity().application as NoteCompletionApplication)
                .abaNotasRepository ,
            (requireActivity().application as NoteCompletionApplication)
                .imgNotasRepository
            )
    }

    private lateinit var tabLayout:TabLayout;
    private lateinit var viewpagr:ViewPager2;
    private lateinit var tabLayoutMediator: TabLayoutMediator;

    private fun carregaDadosAba(abaEnotas: AbaDeNotasWithImagemNotas?){
        var abasDeNotas = tabViewModel.todasAbas.value
        if (abasDeNotas?.isNotEmpty() != true) tabViewModel.criaAbasIniciais()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString("ARG_PARAM1")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabBinding.inflate(inflater,container,false)

        tabViewModel.todasImageNotasEabas.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) carregaDadosAba(null)
            else carregaDadosAba(it.first())
        })
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
            tabViewModel.atualizaAbaAtual()
        })
        tabViewModel.todasAbas.observe(viewLifecycleOwner, Observer {

            it.let{ lista->
                val qtdAbas = tabViewModel.todasAbas.value?.size?: 1
                viewpagr.adapter = TabAdapter(requireActivity(),qtdAbas)
                recriaTabMediator(tabLayout,viewpagr,lista)
            }
        })

        tabViewModel.abaAtualById.observe(viewLifecycleOwner, Observer {
            print(it)
        })

        tabViewModel.posicaoAbaAtual.observe(viewLifecycleOwner, Observer {
            print(it)

        })
        tabViewModel.todasNotas.observe(viewLifecycleOwner, Observer {
            tabViewModel.atualizaAbaAtual()
        })
        tabViewModel.idNotaNova.observe(viewLifecycleOwner, Observer {
            tabViewModel.abaAtualComNotas?.value
                ?.let { abaAtual->
                    abaAtual?.listaDeNotas.let {
                        val posicao = abaAtual?.listaDeNotas!!.size.minus(1)
                        val action = TabFragmentDirections
                            .actionTabFragmentToNotaViewPagerFragment(
                                posicao, true)
                        findNavController().navigate(action)

                    }
                }
        })

        tabViewModel.abaAtualComNotas.observe(viewLifecycleOwner, Observer {
            print("$it")
        })


    }


    override fun onResume() {
        super.onResume()


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val posicaoAtual = tab?.position
                posicaoAtual?.let {
                    tabViewModel.mudaListaParaAbaEm(posicaoAtual)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding.fabAdicionaNota.setOnClickListener {
            if (tabViewModel.tamanhoDaListaCriaRelacionamento.hasObservers())
                tabViewModel.tamanhoDaListaCriaRelacionamento
                    .removeObservers(viewLifecycleOwner)

            val imagemPlaceholdr = getString(R.string.imagemTeste)

            tabViewModel.criaNota(imagemPlaceholdr)


        }
    }

}