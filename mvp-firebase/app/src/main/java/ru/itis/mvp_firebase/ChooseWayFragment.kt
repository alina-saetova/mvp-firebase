package ru.itis.mvp_firebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.itis.mvp_firebase.databinding.FragmentChooseWayBinding

class ChooseWayFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentChooseWayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseWayBinding.inflate(inflater)
        setOnClickListeners()
        return binding.root
    }

    private fun setOnClickListeners() {
        binding.btnPhone.setOnClickListener(this)
        binding.btnEmail.setOnClickListener(this)
        binding.btnGoogle.setOnClickListener(this)
    }



    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnPhone -> findNavController().navigate(R.id.action_chooseWayFragment_to_phoneAuthFragment)
            R.id.btnEmail -> findNavController().navigate(R.id.action_chooseWayFragment_to_emailAuthFragment)
            R.id.btnGoogle -> findNavController().navigate(R.id.action_chooseWayFragment_to_googleAuthFragment)
        }
    }
}
