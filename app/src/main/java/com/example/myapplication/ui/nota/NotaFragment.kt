package com.example.myapplication.ui.nota

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.NoteCompletionApplication
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNotaBinding
import com.example.myapplication.ui.tabs.TabViewModel
import com.example.myapplication.ui.tabs.TabViewModelFactory
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
    private val tabViewModel: TabViewModel by activityViewModels {
        TabViewModelFactory(
            (requireActivity().application as NoteCompletionApplication)
                .abaNotasRepository ,
            (requireActivity().application as NoteCompletionApplication)
                .imgNotasRepository
        )
    }
    private val notaViewModel: NotaViewModel by viewModels {
        NotaViewModelFactory(
            (requireActivity().application as NoteCompletionApplication).imgNotasRepository
        )
    }

    inner class handleTextWatch : Handler(),TextWatcher{
        val pesquisaPalavraDepois= Runnable {
            binding?.let{
                if(it.txtTitulo.text.isNotBlank()){
                    notaViewModel.carregando.value = true
                    notaViewModel
                        .pesquisaImagemRetrofit("${binding.txtTitulo.text}")
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            removeCallbacks(pesquisaPalavraDepois)
        }
        override fun afterTextChanged(s: Editable?) {
            postDelayed(pesquisaPalavraDepois,1000)
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }



    }
    private lateinit var listenerImagemPraTexto: handleTextWatch
    private var  posicaoNotaImagemArmazenada:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            posicaoNotaImagemArmazenada = it.getInt("posicaoNotaSelecionada")
        }
        listenerImagemPraTexto = handleTextWatch()
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
            val todasNotaImgs = tabViewModel.listaAtualById.value
            if(todasNotaImgs!=null){
                notaViewModel.carregaNotaRoom(todasNotaImgs,
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

        binding.txtTitulo.addTextChangedListener(listenerImagemPraTexto)
    }

    override fun onPause() {
        super.onPause()
        println("")
        setHasOptionsMenu(false)
        requireActivity().invalidateOptionsMenu()
        //finish()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        if (findNavController().currentDestination?.id == R.id.NotaViewPagerFragment) {
            inflater.inflate(R.menu.menu_nota, menu)
        }
    }
    private fun editaNotaNaLista(){
        val titulo: String = binding.txtTitulo.text.toString()
        val conteudo: String = binding.txtConteudoNota.text.toString()
        val img: String = "${notaViewModel.fundoImagem.value}"

        notaViewModel.editaNotaAtual(titulo, conteudo, img)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemSalva -> {
                editaNotaNaLista()
                findNavController().navigate(R.id.action_volta_tab_frag)
                //todo findNavController().popBackStack()
                true
            }
            R.id.menu_itemDeletar -> {
                val id = notaViewModel.notaImg.value?.idNota
                findNavController().navigate(R.id.action_volta_tab_frag)
                notaViewModel.deletaNotaAtual()
                true
            }
            R.id.menu_itemCompartilhar->{
                val intentDeCompartilhar: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TITLE, "${binding.txtTitulo.text.toString()}")
                    putExtra(Intent.EXTRA_SUBJECT, "${binding.txtTitulo.text.toString()}")
                    putExtra(Intent.EXTRA_TEXT, "${binding.txtConteudoNota.text.toString()}")
                    type = "text/plain"
                }
                editaNotaNaLista()
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