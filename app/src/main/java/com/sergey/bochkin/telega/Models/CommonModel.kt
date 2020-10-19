package com.sergey.bochkin.telega.Models

data class CommonModel (
    val id: String = "",
    var username: String = "",
    var phone: String = "",
    var bio: String = " ",
    var full_name: String = "",
    var status: String = "",
    var photoUrl: String = "empty"
)