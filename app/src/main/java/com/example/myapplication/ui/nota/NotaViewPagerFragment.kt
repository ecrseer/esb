package com.example.myapplication.ui.nota

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.NoteCompletionApplication
import com.example.myapplication.databinding.FragmentNotaViewPagerBinding
import com.example.myapplication.ui.tabs.TabViewModel
import com.example.myapplication.ui.tabs.TabViewModelFactory


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NotaViewPagerFragment : Fragment() {

    private var _binding: FragmentNotaViewPagerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val tabViewModel: TabViewModel by viewModels {
        TabViewModelFactory(
            (requireActivity().application as NoteCompletionApplication)
                .abaNotasRepository ,
            (requireActivity().application as NoteCompletionApplication)
                .imgNotasRepository
        )
    }
    private var posicao:Int=0
    private var isNotaNovaDeveSerCriada = false
    val args: NotaViewPagerFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotaViewPagerBinding.inflate(inflater, container, false)

        //todo trocar por safe args
        posicao = args?.posicao?: 0

        tabViewModel.abaAtualComNotas.value.let {
            with(binding.pager as ViewPager2){
                adapter =  SliderAdapter(childFragmentManager,lifecycle,
                it?.listaDeNotas?.size)
                currentItem = posicao
            }
        }


        return binding.root

    }





    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().invalidateOptionsMenu()
        _binding = null
    }


}