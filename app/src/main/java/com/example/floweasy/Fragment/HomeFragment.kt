package com.example.floweasy.Fragment

import android.content.Context
import android.os.Bundle
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.floweasy.R
import com.example.floweasy.databinding.FragmentHomeBinding
import com.google.android.material.navigation.NavigationView

class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var selectedCard: String?=null
    private var userName:String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setting the click listener to the cards : card 1 , card 2, card 3
        binding.card1CardView.setOnClickListener { selectCard("Card 1", binding.card1CardView) }
        binding.card2CardView.setOnClickListener { selectCard("Card 2", binding.card2CardView) }
        binding.card3CardView.setOnClickListener { selectCard("Card 3", binding.card3CardView) }

        // setting the click listener to the proceed button
        binding.proceedButton.setOnClickListener {

            // accessing the username from edit view
            userName=binding.userNameEt.text.toString()

            if(!userName.isNullOrEmpty() && !selectedCard.isNullOrEmpty()){
                // if username and selected card both are not null, we have to save the information in shared preferences
                val sharedPreferences= requireActivity().getSharedPreferences("UsersPrefs", Context.MODE_PRIVATE)
                val editor=sharedPreferences.edit()
                editor.putString("username", userName)
                editor.putString("selectedCard", selectedCard)
                editor.apply()  // applying the shared preferences

                // accessing the header view of navigation view to set user name
                val navView = requireActivity().findViewById<NavigationView>(R.id.nav_view)
                val headerView = navView.getHeaderView(0)
                // setting the user name to text view
                val nameUserTextView = headerView.findViewById<TextView>(R.id.name_user)
                nameUserTextView.text = getString(R.string.nav_header_username, userName)

                // navigate to next fragment
                findNavController().navigate(R.id.action_homeFragment_to_nextFragment)
            }else{
                // if there is any needed information blank, we have to handle the event by messaging appropriate message to the user
                handleError()
            }
        }

        // to handle the error message of the edit view we are adding text change listener, to disable the error when user are entering the user name
        binding.userNameEt.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Not need to implemented
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrEmpty()){
                    // set he error of layout to null
                    binding.userNameLayout.error=null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // no need to implement
            }
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    private fun handleError() {

        // we are handling the even to message the user if user name of selected card is null
        if(userName.isNullOrEmpty() && selectedCard.isNullOrEmpty()){
            // if both user name is not entered and card is not selected
            binding.userNameLayout.error="Can't be Blank!"
            setCardViewBgRed()
            Toast.makeText(requireContext(),  "Enter the data before proceeding", Toast.LENGTH_SHORT).show()
        }else if(userName.isNullOrEmpty()){

            // if user name is not entered
            binding.userNameLayout.error="Can't be Blank!"
            Toast.makeText(requireContext(),   "Enter your name", Toast.LENGTH_SHORT).show()
        }else {

            // if card is not selected then we are alerting by setting red color to the card
            setCardViewBgRed()
            Toast.makeText(requireContext(),   "Select any one card", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setCardViewBgRed(){
        // setting the background color to the card and text
        binding.selectCardTextView.setTextColor(resources.getColor(R.color.redis, null))
        binding.card1CardView.setCardBackgroundColor(resources.getColor(R.color.redis, null))
        binding.card1Tv.setTextColor(resources.getColor(R.color.white,null))
        binding.card2CardView.setCardBackgroundColor(resources.getColor(R.color.redis, null))
        binding.card2Tv.setTextColor(resources.getColor(R.color.white, null))
        binding.card3CardView.setCardBackgroundColor(resources.getColor(R.color.redis, null))
        binding.card3Tv.setTextColor(resources.getColor(R.color.white, null))
    }

    private fun selectCard(s: String, selectedCardView: CardView) {
        // reset the text color
        binding.selectCardTextView.setTextColor(resources.getColor(R.color.black, null))

        selectedCard = when(selectedCardView){
            binding.card1CardView->"Card-1"
            binding.card2CardView->"Card-2"
            binding.card3CardView->"Card-3"
            else->null
        }

        // Reset card background colors
        binding.card1CardView.setCardBackgroundColor(resources.getColor(android.R.color.white, null))
        binding.card2CardView.setCardBackgroundColor(resources.getColor(android.R.color.white, null))
        binding.card3CardView.setCardBackgroundColor(resources.getColor(android.R.color.white, null))

        // Set selected card background color
        selectedCardView.setCardBackgroundColor(resources.getColor(R.color.cardButton, null))

    }

}