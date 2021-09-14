package com.example.retrofit_example

import com.google.gson.annotations.SerializedName

data class IDcheck_Response(@SerializedName("exists")var exists: Boolean, @SerializedName("msg")var msg:String) {
}