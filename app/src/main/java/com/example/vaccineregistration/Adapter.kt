package com.example.vaccineregistration

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.ValueEventListener


class Adapter(private val centerList: ArrayList<Center>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    private lateinit var mlistener: MyonClickListener
    interface MyonClickListener{
        fun onClick(position: Int)
    }
    fun setOnItemClick(listener: MyonClickListener){
        mlistener=listener
    }


    class ViewHolder(itemView: View,listener: MyonClickListener) : RecyclerView.ViewHolder(itemView) {

        val centerNameTV: TextView = itemView.findViewById(R.id.idTVCenterName)
        val centerAddressTV: TextView = itemView.findViewById(R.id.idTVCenterAddress)
        val centerTimings: TextView = itemView.findViewById(R.id.idTVCenterTimings)
        val vaccineNameTV: TextView = itemView.findViewById(R.id.idTVVaccineName)
        val centerAgeLimitTV: TextView = itemView.findViewById(R.id.idTVAgeLimit)
        val centerFeeTypeTV: TextView = itemView.findViewById(R.id.idTVFeeType)
        val availabilityTV: TextView = itemView.findViewById(R.id.idTVAvailability)
        init {
            itemView.setOnClickListener{
                val position=adapterPosition
                listener.onClick(position)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.items,
            parent, false
        )

        return ViewHolder(itemView,mlistener)
    }


    override fun getItemCount(): Int {


        return centerList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val currentItem = centerList[position]


        holder.centerNameTV.text = currentItem.centerName
        holder.centerAddressTV.text = currentItem.centerAddress
        holder.centerTimings.text = ("From : " + currentItem.centerFromTime + " To : " + currentItem.centerToTime)
        holder.vaccineNameTV.text = currentItem.vaccineName
        holder.centerAgeLimitTV.text = "Age Limit : " + currentItem.ageLimit.toString()
        holder.centerFeeTypeTV.text = currentItem.fee_type
        holder.availabilityTV.text = "Availability : " + currentItem.availableCapacity.toString()
    }


}
