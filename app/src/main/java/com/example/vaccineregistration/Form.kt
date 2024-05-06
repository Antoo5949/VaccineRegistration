package com.example.vaccineregistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vaccineregistration.databinding.ActivityFormBinding


class Form : AppCompatActivity() {
    private lateinit var binding:ActivityFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val centerName:String= intent.getStringExtra("abc").toString()
        binding.title.setText(centerName)
    }
}