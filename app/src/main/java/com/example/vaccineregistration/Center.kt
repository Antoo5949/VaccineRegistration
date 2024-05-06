package com.example.vaccineregistration

data class Center(


    val centerName: String,

    val centerAddress: String,

    val centerFromTime: String,

    val centerToTime: String,

    var fee_type: String,

    var ageLimit: Int,

    var vaccineName: String,

    var availableCapacity: Int
)


