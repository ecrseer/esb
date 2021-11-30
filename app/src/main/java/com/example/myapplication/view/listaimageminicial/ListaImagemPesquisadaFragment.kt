package com.example.myapplication.view.listaimageminicial

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentImagemItemListBinding
import com.example.myapplication.view.tabs.TabFragmentDirections

/**
 * A fragment representing a list of Items.
 */
class ListaImagemPesquisadaFragment : Fragment() {

    private var _binding: FragmentImagemItemListBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 1
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel =
            ViewModelProvider(requireActivity(), MainViewModelFactory())
                .get(MainViewModel::class.java)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        if(findNavController().currentDestination?.id == R.id.NotaViewPagerFragment){
            inflater.inflate(R.menu.menu_nota,menu)
        }
        else{
            inflater.inflate(R.menu.menu_main,menu)

            val searchWdgt = menu.findItem(R.id.app_bar_search)
            val actionViewPesquisa: SearchView = searchWdgt?.actionView as SearchView

            actionViewPesquisa.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //TODO("Not yet implemented")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return mainViewModel.peneiraNotaPorTexto("$newText")
                }
            })
        }



    }

    val clicarNoItemAbreNota={posicao: Int->
        val acao = TabFragmentDirections.actionTabFragmentToNotaViewPagerFragment(posicao, false)
        findNavController().navigate(acao)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        _binding = FragmentImagemItemListBinding.inflate(inflater, container, false)

        // Set the adapter

        with(binding.list) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            this.addItemDecoration(
                DividerItemDecoration(
                    requireActivity().baseContext,
                    DividerItemDecoration.VERTICAL
                ),
            )
            val listaNotas= mainViewModel.notasImgs.value
            if(listaNotas!=null) {
                adapter = ListaImagemPesquisadaRecyclerViewAdapter(
                    listaNotas, clicarNoItemAbreNota  )
            }
        }//fim do with
        inscreverObserver()

        return binding.root
    }

    private fun inscreverObserver() {
        with(binding.list.adapter as ListaImagemPesquisadaRecyclerViewAdapter){
          mainViewModel.notasImgs.observe(viewLifecycleOwner, Observer {
                this.mudarLista(it)
                //Toast.makeText(activity,"Mudei recyc",Toast.LENGTH_LONG+4242).show()
            })

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.tabFragment2) {
                //requireActivity().invalidateOptionsMenu()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }


}