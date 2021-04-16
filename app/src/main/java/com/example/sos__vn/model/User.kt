package com.example.sos__vn.model

data class User(
    val mid: Int,
    val mname: String,
    val mage: Int,
    val maddress: String,
    val mphone1: String,
    val mphone2: String,
    val mUrl: String,
    val type: Int,
    val location:Location
)
