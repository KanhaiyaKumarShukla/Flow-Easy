package com.example.floweasy.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.floweasy.R
import com.example.floweasy.databinding.FragmentAboutBinding
import com.example.floweasy.databinding.FragmentNextBinding

class NextFragment : Fragment() {

    private var _binding: FragmentNextBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // accessing the shared preferences
        val sharedPreferences = requireActivity().getSharedPreferences("UsersPrefs", Context.MODE_PRIVATE)
        val username = when(val _username=sharedPreferences.getString("username", "Mr/Mrs X")){
            "Mr/Mrs X"->""
            else ->_username
        }
        // setting the username and selected card
        binding.welcomeTextView.text=getString(R.string.welcome_fragment, "Next", username)
        binding.selectedCardTextView.text=getString(R.string.selected_card, sharedPreferences.getString("selectedCard", "No Card"))

    }


}