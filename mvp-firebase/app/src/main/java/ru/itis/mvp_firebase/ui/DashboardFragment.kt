package ru.itis.mvp_firebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.mvp_firebase.R
import ru.itis.mvp_firebase.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater)
        binding.btnBackToMvp.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dashboard_to_mainActivity)
        }
        return binding.root
    }
}
