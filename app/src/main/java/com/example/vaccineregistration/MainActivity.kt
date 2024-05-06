package com.example.vaccineregistration

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vaccineregistration.databinding.ActivityMainBinding
import com.google.firebase.database.*



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    private lateinit var Rv: RecyclerView
    private lateinit var centerList: ArrayList<Center>
    private lateinit var centerRVAdapter: Adapter
    private lateinit var centerName: String


    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Rv = binding.centersRV
        Rv.layoutManager = LinearLayoutManager(this)
        Rv.setHasFixedSize(true)
        centerList = arrayListOf<Center>()
        filter()


    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun filter() {


        database =
            FirebaseDatabase.getInstance(" https://vaccine-registration-bca1b-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Hostpitals")
        binding.BtnSearch.setOnClickListener {

            val c = Calendar.getInstance()


            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    binding.PBLoading.setVisibility(View.VISIBLE)


                    val dateStr: String = """$dayOfMonth - ${monthOfYear + 1} - $year"""
                    // getData()
                    filterGender()


                },
                year,
                month,
                day
            )

            dpd.show()
        }

    }

    private fun getData() {
        database.child("S1").get().addOnSuccessListener {


            println("hi")

            if (it.exists()) {
                val centerName = it.child("Center Name").value
                val centerAddress = it.child("Center Address").value
                val centerFromTime = it.child("Timings").value
                val centerToTime = it.child("Vaccine Name").value
                val fee_type = it.child("Fee Type").value
                val ageLimit = it.child("Age Limit").value
                val vaccineName = it.child("Vaccine Name").value
                val availableCapacity = it.child("Avaibility").value

                val center = Center(
                    centerName.toString(),
                    centerAddress.toString(),
                    centerFromTime.toString(),
                    centerToTime.toString(),
                    fee_type.toString(),
                    ageLimit.toString().toInt(),
                    vaccineName.toString(),
                    availableCapacity.toString().toInt()

                )
                binding.PBLoading.setVisibility(View.GONE)
                centerList.add(center)
                centerRVAdapter = Adapter(centerList)


                // on the below line we are setting an adapter to our recycler view.

                Rv.adapter = centerRVAdapter
                centerRVAdapter.setOnItemClick(object : Adapter.MyonClickListener {
                    override fun onClick(position: Int) {
                        //Toast.makeText(this@MainActivity,"you clicked on item no. $position",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, Form::class.java)
                        startActivity(intent)

                    }

                })


                // on the below line we are notifying our adapter as the data is updated.
                centerRVAdapter.notifyDataSetChanged()

            } else {
                println("asd")
            }

        }

    }

    private fun filterGender() {
        // Specifying path and filter category and adding a listener
        database.orderByChild("Center Address").equalTo("Mohmmadpur")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        centerList.clear()
                        println("Yesss")
                        for (i in snapshot.children) {
                             centerName = i.child("Center Name").value as String
                            val centerAddress = i.child("Center Address").value
                            val centerFromTime = i.child("Timings").value
                            val centerToTime = i.child("Vaccine Name").value
                            val fee_type = i.child("Fee Type").value
                            val ageLimit = i.child("Age Limit").value
                            val vaccineName = i.child("Vaccine Name").value
                            val availableCapacity = i.child("Avaibility").value

                            val center = Center(
                                centerName,
                                centerAddress.toString(),
                                centerFromTime.toString(),
                                centerToTime.toString(),
                                fee_type.toString(),
                                ageLimit.toString().toInt(),
                                vaccineName.toString(),
                                availableCapacity.toString().toInt()

                            )

                            centerList.add(center)



                            // on the below line we are setting an adapter to our recycler view.


                        }
                        binding.PBLoading.setVisibility(View.GONE)
                        centerRVAdapter = Adapter(centerList)
                        Rv.adapter = centerRVAdapter
                        Rv.adapter = centerRVAdapter
                        centerRVAdapter.setOnItemClick(object : Adapter.MyonClickListener {
                            override fun onClick(position: Int) {
                                //Toast.makeText(this@MainActivity,"you clicked on item no. $position",Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@MainActivity, Form::class.java)
                                intent.putExtra("abc",centerList[position].centerName)
                                startActivity(intent)

                            }

                        })


                        // on the below line we are setting an adapter to our recycler view.


                    } else {
                        Toast.makeText(applicationContext, "Data is not found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }


}


















