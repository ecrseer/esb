package com.example.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import android.text.TextWatcher;
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import kotlin.reflect.typeOf

class HomeFragment : Fragment() {
    fun htpPost(){
        val client = OkHttpClient()

        val request = Request.Builder().url("https://gorest.co.in/public/v1/users").get().build()

        doAsync{
            val response = client.newCall(request).execute()
            Log.d("HomeFragt","getand$response")
            var json = "nadinha"
            if(response.body()!==null) {
                json = response.body()!!.string();
            }
            Log.d("HomeFragt","getei o $json")

         }


    }
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        val meuinputView: EditText = binding.myinputHome
        val meusimbora_btn: Button = binding.simboraBtn

        var valorInput = MutableLiveData<String>().apply { value = "Digite algo" }

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        homeViewModel.input.observe(viewLifecycleOwner,Observer{ito->
            meuinputView.setText(ito)
            Log.d("HomeFragt","oioi${ito}")
        })
        meuinputView.setOnKeyListener { v, keyCode, event ->
                if((keyCode == KeyEvent.KEYCODE_SPACE) && (event.action == KeyEvent.ACTION_DOWN)){
                    val txt = meuinputView.text
                    Log.d("HomeFragt","batatas de ${txt} "+v::class.simpleName)
                    htpPost()

                    return@setOnKeyListener false;
                }
           return@setOnKeyListener false;


        }

//        meuinputView.onKeyDown(KeyEvent.KEYCODE_SPACE,KeyEvent())

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}