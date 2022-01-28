package com.imeofon.roomdatabase.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.imeofon.roomdatabase.R
import com.imeofon.roomdatabase.model.User
import kotlinx.android.synthetic.main.card_layout.view.*
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.id_txt.text = currentItem.id.toString()
        holder.itemView.lagos.text = currentItem.enterDeparture
        holder.itemView.date.text = currentItem.enterDate
        holder.itemView.time.text = currentItem.enterTime
        holder.itemView.london.text = currentItem.enterDestination
        holder.itemView.date2.text = currentItem.enterDate2
        holder.itemView.time2.text = currentItem.enterTime2
        holder.itemView.trip_type.text = currentItem.enterTripType

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment2(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(user: List<User>) {
        this.userList = user
        notifyDataSetChanged()
    }
}






