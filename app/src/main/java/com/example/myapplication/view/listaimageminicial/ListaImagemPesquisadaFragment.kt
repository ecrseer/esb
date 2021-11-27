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
import com.example.myapplication.view.tabs.TabFragmentDirections

/**
 * A fragment representing a list of Items.
 */
class ListaImagemPesquisadaFragment : Fragment() {

    private var columnCount = 1
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mh: MainHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true);
        arguments?.let {
            columnCount = it.getInt("ARG_COLUMN_COUNT")
        }

        mainViewModel =
            ViewModelProvider(requireActivity(), MainViewModelFactory())[MainViewModel::class.java]

        mh = MainHandler(mainViewModel.peneiraNotaPorTexto)

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        //requireActivity().invalidateOptionsMenu()
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()

        inflater.inflate(R.menu.menu_main, menu)
        val searchWdgt = menu.findItem(R.id.app_bar_search)
        val actionViewPesquisa: SearchView = searchWdgt?.actionView as SearchView

        actionViewPesquisa.setOnQueryTextListener(mh)


    }

    fun clicarNoItemAbreNota(posicao:Int){
        val acao = TabFragmentDirections.actionTabFragmentToNotaViewPagerFragment(posicao,false)

        findNavController().navigate(acao)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_imagem_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                this.addItemDecoration(
                    DividerItemDecoration(requireActivity().baseContext,
                        DividerItemDecoration.VERTICAL),

                )




                //todo:delay on observer
                mainViewModel.notasImgs.observe(viewLifecycleOwner,Observer{
                    adapter = ListaImagemPesquisadaRecyclerViewAdapter(it){
                            posicao->clicarNoItemAbreNota(posicao)
                    }
                    //Toast.makeText(activity,"Mudei recyc",Toast.LENGTH_LONG+4242).show()
                })
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id==R.id.tabFragment2){
                //requireActivity().invalidateOptionsMenu()
            }
        }
    }

}