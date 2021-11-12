package com.example.myapplication

import ImagemPesquisada
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFirstBinding
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import java.io.InputStream
import java.lang.Exception
import java.net.URL


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var firstFragmentViewModel : FirstFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        firstFragmentViewModel = ViewModelProvider(this)
            .get(FirstFragmentViewModel::class.java)

        firstFragmentViewModel.input.observe(viewLifecycleOwner, Observer{it->
            binding.myinputHome.setText(it);
        })
        firstFragmentViewModel.ldImagem.observe(viewLifecycleOwner,Observer{
            novaUrlimg -> Picasso.get().load(novaUrlimg).into(binding.idImgFirst)
        })



      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //childFragmentManager
        populaImagemPlacehld()
        binding.buttonFirst.setOnClickListener {
            NoteImagens.imgs.add
            findNavController().navigate(R.id.action_voltarParaListaNotas)

        }

        binding.myinputHome.setOnKeyListener { v, keyCode, event ->
            if((keyCode == KeyEvent.KEYCODE_SPACE) &&
                (event.action == KeyEvent.ACTION_DOWN)){
                firstFragmentViewModel
                    .pesquisaImagemDe("${binding.myinputHome.text}")

                return@setOnKeyListener false;
            }
            return@setOnKeyListener false;


        }



    }

    private fun populaImagemPlacehld() {
        val placehd = getString(R.string.imagemTeste)
        firstFragmentViewModel.setLdImagem(placehd)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}