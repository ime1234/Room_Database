package com.imeofon.roomdatabase.fragments.add

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.imeofon.roomdatabase.R
import com.imeofon.roomdatabase.model.User
import com.imeofon.roomdatabase.usermodel.UserViewModel
import kotlinx.android.synthetic.main.card_layout.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        //Popup menu
        view.enter_trip.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(),enter_trip)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.business ->
                        enter_trip.setText(R.id.title)
                    R.id.vacation ->
                        enter_trip.setText(R.id.title)
                    R.id.education ->
                        enter_trip.setText(R.id.title)
                    R.id.medical ->
                        enter_trip.setText(R.id.title)
                }
                true
            }

        }

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        view.enter_date.setOnClickListener {
            selectDate(it)
        }

        view.enter_date2.setOnClickListener {
            selectDate(it)
        }

        view.enter_time.setOnClickListener {
            selectTime(it)
        }

        view.enter_time2.setOnClickListener {
            selectTime(it)
        }

        return view

    }

    private fun selectDate(v: View) {
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, dayOfMonth ->
                val formattedDate = formatDate(mYear, mMonth, dayOfMonth)

                (v as EditText).text = formattedDate.toEditable()
            },
            year,
            month,
            day
        ).show()
    }

    private fun selectTime(v: View) {
        val c = Calendar.getInstance()

        val hourOfDay = c.get(Calendar.HOUR_OF_DAY)
        val minOfDay = c.get(Calendar.MINUTE)

        TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hour, min ->
                val am_pm = if (hour >= 12) "PM" else "AM"
                val time =
                    if (hour == 12 || hour == 0) "12 : $min $am_pm" else "${hour % 12} : $min $am_pm"
                (v as EditText).text = time.toEditable()
            },
            hourOfDay,
            minOfDay,
            false
        ).show()
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun formatDate(year: Int, month: Int, day: Int): String {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDate.of(year, month, day)
            val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy")
            val answer: String = date.format(formatter)
            Log.d("answer", answer)
            answer
        } else {

            var date = Date(year - 1900, month, day)
            val formatter = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
            val answer: String = formatter.format(date)
            Log.d("answer", answer)
            Log.d("answerYear", year.toString())
            answer

        }
    }

     private fun insertDataToDatabase() {
      val enterDeparture = enter_departure.text.toString()
      val enterDate = enter_date.text.toString()
      val enterTime = enter_time.text.toString()
      val enterDestination = enter_destination.text.toString()
      val enterDate2 = enter_date2.text.toString()
      val enterTime2 = enter_time2.text.toString()
      val tripType = enter_trip.text.toString()


        if (inputCheck(enterDeparture, enterDate, enterTime, enterDestination, enterDate2, enterTime2, tripType)){
            //Create User Object
                val user = User(0, enterDeparture, enterDate, enterTime, enterDestination, enterDate2, enterTime2, tripType )

            //Add Data to Database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added trip!", Toast.LENGTH_LONG).show()
            //Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{

            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }

    }

    private fun inputCheck(
        enterDeparture: String,
        enterDate: String,
        enterTime: String,
        enterDestination: String,
        enterDate2: String,
        enterTime2: String,
        tripType: String
    ): Boolean {
        return !(TextUtils.isEmpty(enterDeparture) &&
                TextUtils.isEmpty(enterDate) &&
                TextUtils.isEmpty(enterTime) &&
                TextUtils.isEmpty(enterDestination) &&
                TextUtils.isEmpty(enterDate2) &&
                TextUtils.isEmpty(enterTime2) &&
                TextUtils.isEmpty(tripType))
    }

}