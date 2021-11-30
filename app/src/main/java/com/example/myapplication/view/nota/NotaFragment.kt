package com.example.myapplication.view.nota

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainViewModel
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

    private lateinit var mainViewModel: MainViewModel
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
        // Inflate the layout for this fragment

        return binding.root
    }


    fun inscreverObservers(){
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
        })



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtTitulo.setOnKeyListener { v, keyCode, event ->
            if ((keyCode == KeyEvent.KEYCODE_SPACE) &&
                (event.action == KeyEvent.ACTION_DOWN)
            ) {
                notaViewModel
                    .pesquisaImagemRetrofit("${binding.txtTitulo.text}")
            }
            return@setOnKeyListener false;

        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemSalva -> {
                val titulo: String = binding.txtTitulo.text.toString()
                val conteudo: String = binding.txtConteudoNota.text.toString()
                val img: String = "${notaViewModel.fundoImagem.value}"

                notaViewModel.editaNotaAtual(titulo, conteudo, img)

                findNavController().navigate(R.id.action_NotaViewPagerFragment_to_tabFragment2)
                true
            }
            else -> false
        }
    }







    override fun onPause() {
        super.onPause()
        println("")
        setHasOptionsMenu(false)
        //finish()
    }

    fun carregaDados(){
        mainViewModel =
            ViewModelProvider(requireActivity(), MainViewModelFactory())
                .get(MainViewModel::class.java)
        if(posicaoNotaImagemArmazenada!=null){
            val todasNotaImgs = mainViewModel.notasImgs.value
            if(todasNotaImgs!=null){
                inscreverObservers()
                notaViewModel.carregaNotaSalva(todasNotaImgs,
                    posicaoNotaImagemArmazenada!!)
                setHasOptionsMenu(true)
            }

        }
    }
    override fun onResume() {
        super.onResume()

        if(isVisible){
            carregaDados()
        }

        binding.btnSalvar.setOnClickListener {
            val titulo: String = binding.txtTitulo.text.toString()
            val conteudo: String = binding.txtConteudoNota.text.toString()
            val img: String = "${notaViewModel.fundoImagem.value}"
            notaViewModel.editaNotaAtual(titulo,conteudo,img)

            findNavController().navigate(R.id.action_NotaViewPagerFragment_to_tabFragment2)

        }

    }




}