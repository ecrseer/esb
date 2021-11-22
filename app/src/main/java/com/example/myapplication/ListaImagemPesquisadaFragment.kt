package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class ListaImagemPesquisadaFragment : Fragment() {

    private var columnCount = 1
    private lateinit var firstFragmentViewModel: FirstFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }
    fun clicarNoItemAbreNota(posicao:Int){
            startActivity(
                Intent(activity,ModoLivroActivity::class.java)
                    .putExtra("posicao",posicao)
            )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_imagem_item_list, container, false)

        firstFragmentViewModel =
            ViewModelProvider(requireActivity(),MainViewModelFactory())[FirstFragmentViewModel::class.java]

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }


                adapter = MyListaImagemPesquisadaRecyclerViewAdapter(firstFragmentViewModel.notasImgs.value!!){
                    posicao->clicarNoItemAbreNota(posicao)
                }
                firstFragmentViewModel.notasImgs.observe(viewLifecycleOwner,Observer{
                    adapter = MyListaImagemPesquisadaRecyclerViewAdapter(it){
                            posicao->clicarNoItemAbreNota(posicao)
                    }
                    Toast.makeText(activity,"Mudei recyc",Toast.LENGTH_LONG+4242).show()
                })
            }
        }
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ListaImagemPesquisadaFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}