package com.example.floweasy.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.floweasy.R
import com.example.floweasy.databinding.FragmentAboutBinding
import com.example.floweasy.databinding.FragmentPrivacyBinding


class PrivacyFragment : Fragment() {

    private var _binding: FragmentPrivacyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPrivacyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireActivity().getSharedPreferences("UsersPrefs", Context.MODE_PRIVATE)
        val username = when(val _username=sharedPreferences.getString("username", "Mr/Mrs X")){
            "Mr/Mrs X"->""
            else ->_username
        }
        binding.welcomeTextView.text=getString(R.string.welcome_fragment, "Privacy", username)
    }

}