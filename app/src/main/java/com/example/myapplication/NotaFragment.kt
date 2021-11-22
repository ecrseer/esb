package com.example.myapplication

import ImagemPesquisada
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentNotaBinding
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [NotaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotaFragment : Fragment() {
    private var _binding: FragmentNotaBinding?=null
    private val binding get() = _binding!!
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private lateinit var mainViewModel: MainViewModel
    private var  notaImagemArmazenada:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("ARG_PARAM1")
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
        mainViewModel =
            ViewModelProvider(requireActivity(),MainViewModelFactory())[MainViewModel::class.java]

        mainViewModel.input.observe(viewLifecycleOwner, Observer { it ->
            binding.txtTitulo.setText(it);
        })
        mainViewModel.ldImagem.observe(viewLifecycleOwner, Observer { novaUrlimg ->
            Picasso.get().load(novaUrlimg).into(binding.idImgFirst)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //childFragmentManager

        if(notaImagemArmazenada!=null){
            Toast.makeText(context,"carregando",
                Toast.LENGTH_LONG+4234)
                .show()

            val notaClicada=NoteImagens.imgs[notaImagemArmazenada!!]
            mainViewModel.carregaNotaSalva(notaClicada)
        }
        binding.btnSalvar.setOnClickListener {
            val conteudo: String = binding.txtConteudoNota.text.toString()
            val titulo: String = "${binding.txtTitulo.text.toString()}"
            val img: String = "${mainViewModel.ldImagem.value}"

            val imgP=ImagemPesquisada(
                "${img}", "${img}",
                "${conteudo}", "${titulo}"
            )
            //todo:verificar se esta sendo editado ou se é nova nota
            NoteImagens.imgs.add(imgP)
            findNavController().navigate(R.id.action_voltarParaListaNotas)

        }

        binding.txtTitulo.setOnKeyListener { v, keyCode, event ->
            if ((keyCode == KeyEvent.KEYCODE_SPACE) &&
                (event.action == KeyEvent.ACTION_DOWN)
            ) {
                mainViewModel
                    .pesquisaImagemRetrofit("${binding.txtTitulo.text}")

            }
            return@setOnKeyListener false;


        }


    }

}