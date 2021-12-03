package com.example.myapplication.ui.nota

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.ui.listaimageminicial.ListaNotasViewModel
import com.example.myapplication.MainViewModelFactory
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNotaBinding
import com.squareup.picasso.Picasso

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [NotaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotaFragment : Fragment() {
    companion object {
        fun newInstance() = NotaFragment()
    }
    private var _binding: FragmentNotaBinding?=null
    private val binding get() = _binding!!

    private lateinit var listaNotasViewModel: ListaNotasViewModel
    private lateinit var notaViewModel: NotaViewModel

    private var  posicaoNotaImagemArmazenada:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            posicaoNotaImagemArmazenada = it.getInt("posicao")
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNotaBinding.inflate(inflater,container,false)
        inscreverObservers()
        return binding.root
    }


    fun inscreverObservers(){
        listaNotasViewModel =
            ViewModelProvider(requireActivity(), MainViewModelFactory())
                .get(ListaNotasViewModel::class.java)

        notaViewModel = ViewModelProvider(this).get( NotaViewModel::class.java)
        val titulo = notaViewModel.tituloNota.value

        notaViewModel.tituloNota.observe(viewLifecycleOwner, Observer { it ->
            binding.txtTitulo.setText(it);
        })
        notaViewModel.textoNota.observe(viewLifecycleOwner, Observer { it ->
            binding.txtConteudoNota.setText(it)
        })
        notaViewModel.fundoImagem.observe(viewLifecycleOwner, Observer { novaUrlimg ->

            Picasso.get().load(novaUrlimg)
                //.centerInside()
                .centerCrop().resize(580, 700)
                .into(binding.idImgFirst)
            notaViewModel.carregando.value=false
        })
        notaViewModel.carregando.observe(viewLifecycleOwner, Observer {isCarregando->
            if(isCarregando) binding.progressBar.visibility=View.VISIBLE
            else binding.progressBar.visibility=View.GONE

        })

    }
    fun carregaDadosDaNota(){

        if(posicaoNotaImagemArmazenada!=null){
            val todasNotaImgs = listaNotasViewModel.notasImgs.value
            if(todasNotaImgs!=null){
                notaViewModel.carregaNotaSalva(todasNotaImgs,
                    posicaoNotaImagemArmazenada!!)

            }

        }
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        if(isVisible){
            carregaDadosDaNota()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtTitulo.setOnKeyListener { v, keyCode, event ->
            if ((keyCode == KeyEvent.KEYCODE_SPACE) &&
                (event.action == KeyEvent.ACTION_DOWN)
            ) {
                notaViewModel.carregando.value = true
                notaViewModel
                    .pesquisaImagemRetrofit("${binding.txtTitulo.text}")
            }
            return@setOnKeyListener false;

        }


    }
    private fun salvaNotaNaLista(){
        val titulo: String = binding.txtTitulo.text.toString()
        val conteudo: String = binding.txtConteudoNota.text.toString()
        val img: String = "${notaViewModel.fundoImagem.value}"

        notaViewModel.editaNotaAtual(titulo, conteudo, img)
    }

    override fun onPause() {
        super.onPause()
        println("")
        setHasOptionsMenu(false)
        requireActivity().invalidateOptionsMenu()
        //finish()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemSalva -> {
                salvaNotaNaLista()
                findNavController().navigate(R.id.action_NotaViewPagerFragment_to_tabFragment2)
                //todo findNavController().popBackStack()
                true
            }
            R.id.menu_itemDeletar -> {
                val id = notaViewModel.notaImg.value?.id
                findNavController().navigate(R.id.action_NotaViewPagerFragment_to_tabFragment2)
                listaNotasViewModel.deletaNota(id!!)
            }
            R.id.menu_itemCompartilhar->{
                val intentDeCompartilhar: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TITLE, "${binding.txtTitulo.text.toString()}")
                    putExtra(Intent.EXTRA_SUBJECT, "${binding.txtTitulo.text.toString()}")
                    putExtra(Intent.EXTRA_TEXT, "${binding.txtConteudoNota.text.toString()}")
                    type = "text/plain"
                }
                salvaNotaNaLista()
                val pm = requireActivity().packageManager
                val existeAplicativoQuePossaCompartilhar = intentDeCompartilhar.resolveActivity(pm)!=null
                if(existeAplicativoQuePossaCompartilhar){
                    val shareIntent = Intent.createChooser(intentDeCompartilhar, null)
                    startActivity(shareIntent)
                    true
                }
                false
            }
            else -> false
        }

    }

}