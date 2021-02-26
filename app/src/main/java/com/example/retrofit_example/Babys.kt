package com.example.retrofit_example

import com.google.gson.annotations.SerializedName

data class Babys(@SerializedName("success")var success: Boolean, @SerializedName("result")var result:List<String>) {

}