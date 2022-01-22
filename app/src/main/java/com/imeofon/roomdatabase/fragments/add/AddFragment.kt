package com.imeofon.roomdatabase.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.imeofon.roomdatabase.R
import com.imeofon.roomdatabase.model.User
import com.imeofon.roomdatabase.usermodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        return view

    }

    private fun insertDataToDatabase() {
      val firstName = first_name.text.toString()
      val lastName = last_name.text.toString()
      val age = age.text


        if (inputCheck(firstName, lastName, age)){
            //Create User Object
                val user = User(0, firstName, lastName, Integer.parseInt(age.toString()))

            //Add Data to Database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            //Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{

            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }

    }

    private fun inputCheck(firstNAme: String, lastName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstNAme) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }
}
