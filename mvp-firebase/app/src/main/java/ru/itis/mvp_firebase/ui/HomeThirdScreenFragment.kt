package ru.itis.mvp_firebase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import ru.itis.mvp_firebase.R
import ru.itis.mvp_firebase.databinding.FragmentHomeThirdScreenBinding

class HomeThirdScreenFragment : Fragment() {

    lateinit var binding: FragmentHomeThirdScreenBinding
    val args: HomeThirdScreenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeThirdScreenBinding.inflate(inflater)
        binding.tvTitle.text = args.titleArg
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.back_to_home)
        }
        return binding.root
    }

}
