package com.example.myapplication.ui.listaimageminicial

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentImagemItemListBinding
import com.example.myapplication.domain.AbaDeNotas
import com.example.myapplication.domain.AbaDeNotasWithImagemNotas
import com.example.myapplication.domain.ImagemNota
import com.example.myapplication.ui.tabs.TabFragmentDirections

/**
 * A fragment representing a list of Items.
 */
class ListaImagemPesquisadaFragment : Fragment() {

    private var _binding: FragmentImagemItemListBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaNotasViewModel: ListaNotasViewModel

    private var columnCount = 1
    private var isListaFavoritos = false

    private fun renovaListaAdapter(listaNotasInicial:List<ImagemNota>?){
        val clicarNoItemAbreNota = { posicao: Int ->

            val acao = TabFragmentDirections.actionTabFragmentToNotaViewPagerFragment(posicao, false)
            findNavController().navigate(acao)
        }
        with(binding.list) {
            if(listaNotasInicial!=null) {
                adapter = ListaImagemPesquisadaRecyclerViewAdapter(
                    listaNotasInicial, clicarNoItemAbreNota  )
            }
        }
    }

    private val listenerAtualizaScroll = object : NavController.OnDestinationChangedListener{
        override fun onDestinationChanged(
            controller: NavController,
            destination: NavDestination,
            arguments: Bundle?
        ) {
            with(binding.list) {
                if(listaNotasViewModel.notaImgsDoRoom.value!=null)
                    this.scrollToPosition(listaNotasViewModel
                        .notaImgsDoRoom.value!!.size)
            }
        }

    }
    private val listenerPesquisaNotas = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {return true}
        override fun onQueryTextChange(newText: String?): Boolean {
            if(newText?.isBlank() == true){
                renovaListaAdapter(listaNotasViewModel.notaImgsDoRoom.value)
                return false
            }else{
                val resultadoPesquisa = listaNotasViewModel.getListaNotasPesquisadas(newText)
                renovaListaAdapter(resultadoPesquisa)
                return true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isListaFavoritos = it.getBoolean("isListaFavoritos")
        }

        listaNotasViewModel =
            ViewModelProvider(requireActivity(), MainViewModelFactory(
                requireActivity().application))
                .get(ListaNotasViewModel::class.java)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentImagemItemListBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        if (findNavController().currentDestination?.id == R.id.NotaViewPagerFragment) {
            inflater.inflate(R.menu.menu_nota, menu)
        } else {
            inflater.inflate(R.menu.menu_main, menu)
            val searchWdgt = menu.findItem(R.id.app_bar_search)
            val actionViewPesquisa: SearchView = searchWdgt?.actionView as SearchView
            actionViewPesquisa.setOnQueryTextListener(listenerPesquisaNotas)
        }

    }

     private fun defineRecyclerView() {
        with(binding.list) {

            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }

        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().addOnDestinationChangedListener(listenerAtualizaScroll)

    }

    override fun onPause() {
        super.onPause()
        findNavController().removeOnDestinationChangedListener(listenerAtualizaScroll)

    }

    override fun onResume() {
        super.onResume()
        defineRecyclerView()
        listaNotasViewModel.notaImgsDoRoom.observe(viewLifecycleOwner, Observer {
            println("$it")
            renovaListaAdapter(it)
        })
    }




}