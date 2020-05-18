package ru.itis.mvp_firebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.mvp_firebase.R
import ru.itis.mvp_firebase.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.btnNextScreen.setOnClickListener {
            findNavController().navigate(R.id.home_to_nested_graph)
        }
        return binding.root
    }
}
