package com.example.myapplication.view.nota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.MainViewModel
import com.example.myapplication.MainViewModelFactory
import com.example.myapplication.model.NoteImagens
import com.example.myapplication.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: FirstFragmentArgs by navArgs()

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        mainViewModel =
            ViewModelProvider(requireActivity(), MainViewModelFactory())[MainViewModel::class.java]

        arguments?.let {
            //notaImagemArmazenada = it.getInt("posicao")
        }
        val adaptr = ScreenSlidePagerAdapter(activity!!)
        binding.pager.adapter = adaptr

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val posicao:Int = args.posicao
        binding.pager.currentItem = 1
        }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
      inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = mainViewModel.notasImgs.value?.size ?: 3

          override fun getItemId(position: Int): Long {
              return super.getItemId(position)
          }

        override fun createFragment(position: Int): Fragment {
            val frag = NotaFragment()


            val bundl = Bundle()
            bundl.putInt("posicao",args.posicao)
            frag.arguments = bundl

            return frag;
        }


      }
}