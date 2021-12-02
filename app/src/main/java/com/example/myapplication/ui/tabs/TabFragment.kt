package com.example.myapplication.ui.tabs

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentTabBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


/**
 * A simple [Fragment] subclass.
 * Use the [TabFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabFragment : Fragment() {
    private var _binding: FragmentTabBinding?=null
    private val binding get()= _binding!!

    private var param1: String? = null
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString("ARG_PARAM1")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.tabLayout
        val viewpagr = binding.viewpager
        viewpagr.adapter = TabAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewpagr){
                tab,position->
            viewpagr.setCurrentItem(tab.position,true)
            if(position==0) tab.text="todas"
            if(position==1) tab.text="favoritas"

        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val posicaoAtual=tab?.position
                if(posicaoAtual!=null){
                    mainViewModel.notasImgs
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })



    }

}