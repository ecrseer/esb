package com.example.myapplication.ui.nota

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentNotaViewPagerBinding
import com.example.myapplication.ui.listaimageminicial.ListaNotasViewModel
import kotlinx.coroutines.runBlocking


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NotaViewPagerFragment : Fragment() {

    private var _binding: FragmentNotaViewPagerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var listaNotasViewModel: ListaNotasViewModel
    private var posicao:Int=0
    private var isNotaNovaDeveSerCriada = false
    val args: NotaViewPagerFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotaViewPagerBinding.inflate(inflater, container, false)

        //todo trocar por safe args
        listaNotasViewModel =
            ViewModelProvider(requireActivity(),
                MainViewModelFactory(requireActivity().application))
                .get(ListaNotasViewModel::class.java)

        if(args!=null){
            if( args.posicao!=null){
                posicao = args.posicao
            }
        }

        val adaptr = SliderAdapter(childFragmentManager,lifecycle,
            listaNotasViewModel.notaImgsDoRoom.value?.size,false)

        with(binding.pager as ViewPager2){
            adapter =  adaptr
            currentItem = posicao
        }


        return binding.root

    }





    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().invalidateOptionsMenu()
        _binding = null
    }
    fun avan(){
        if(args.isNotaNova){
            runBlocking {
                with(binding.pager as ViewPager2){

                    currentItem = posicao+1
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()


    }

}