package com.example.dummyypapp.model

import com.google.gson.annotations.SerializedName


data class UserResponse(

    @field:SerializedName("data")
    val data: List<UserDataItem>,


)

data class UserDataItem(

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("firstName")
    val firstName: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("lastName")
    val lastName: String? = null,

    @field:SerializedName("picture")
    val picture: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,


    @field:SerializedName("email")
    val email: String? = null,


    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    )

data class Location(

    @field:SerializedName("street")
    val street: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("state")
    val state: String? = null,

    @field:SerializedName("country")
    val country: String? = null,

    @field:SerializedName("timezone")
    val timezone: String? = null,

    )