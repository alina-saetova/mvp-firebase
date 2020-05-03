package ru.itis.mvp_firebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import ru.itis.mvp_firebase.databinding.FragmentUserDataBinding

class UserDataFragment : Fragment() {

    private lateinit var binding: FragmentUserDataBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = FirebaseAuth.getInstance()
        binding = FragmentUserDataBinding.inflate(inflater)
        binding.tv.text = firebaseAuth.currentUser?.uid
        return binding.root
    }

}
