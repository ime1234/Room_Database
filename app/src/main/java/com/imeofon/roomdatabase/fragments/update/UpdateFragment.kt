package com.imeofon.roomdatabase.fragments.update

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.imeofon.roomdatabase.R
import com.imeofon.roomdatabase.model.User
import com.imeofon.roomdatabase.usermodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class UpdateFragment : Fragment() {

   private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        //Popup menu for the update
        view.update_type.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(),update_type)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.business ->
                        update_type.setText(R.id.title)
                    R.id.vacation ->
                        update_type.setText(R.id.title)
                    R.id.education ->
                        update_type.setText(R.id.title)
                    R.id.medical ->
                        update_type.setText(R.id.title)
                }
                true
            }

        }

       view.update_departure.setText(args.currentUser.enterDeparture)
       view.update_date.setText(args.currentUser.enterDate)
       view.update_time.setText(args.currentUser.enterTime)
       view.update_destination.setText(args.currentUser.enterDestination)
       view.update_date2.setText(args.currentUser.enterDate2)
       view.update_time2.setText(args.currentUser.enterTime2)
       view.update_type.setText(args.currentUser.enterTripType)

        view.update_btn.setOnClickListener {
            updateItem()
        }

        view.update_date.setOnClickListener {
            selectDate(it)
        }

        view.update_date2.setOnClickListener {
            selectDate(it)
        }

        view.update_time.setOnClickListener {
            selectTime(it)
        }

        view.update_time2.setOnClickListener {
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

    private fun updateItem(){
        val enterDeparture = update_departure.text.toString()
        val enterDate = update_date.text.toString()
        val enterTime = update_time.text.toString()
        val enterDestination = update_destination.text.toString()
        val enterDate2 = update_date2.text.toString()
        val enterTime2 = update_time2.text.toString()
        val enterTripType = update_type.text.toString()

        if (inputCheck(enterDeparture, enterDate, enterTime, enterDestination, enterDate2, enterTime2, enterTripType)) {
            //Create user object
            val updatedUser = User(args.currentUser.id, enterDeparture, enterDate, enterTime, enterDestination, enterDate2, enterTime2, enterTripType)
            // Update Current User
            mUserViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()

            //  Navigation Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment2)
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

