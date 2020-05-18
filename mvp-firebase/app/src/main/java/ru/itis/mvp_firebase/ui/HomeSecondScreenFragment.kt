package ru.itis.mvp_firebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.mvp_firebase.R
import ru.itis.mvp_firebase.databinding.FragmentHomeSecondScreenBinding

class HomeSecondScreenFragment : Fragment() {

    lateinit var binding: FragmentHomeSecondScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeSecondScreenBinding.inflate(inflater)
        val actionHomeThird =
            HomeSecondScreenFragmentDirections.actionHomeSecondScreenFragmentToHomeThirdScreenFragment(
                "it's home third screen")
        binding.btnNextScreen.setOnClickListener {
            findNavController().navigate(actionHomeThird)
        }
        return binding.root
    }

}
