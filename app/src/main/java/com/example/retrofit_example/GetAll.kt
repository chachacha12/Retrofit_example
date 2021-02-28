package com.example.retrofit_example

import com.google.gson.annotations.SerializedName

data class GetAll(@SerializedName("success")var success: Boolean, @SerializedName("result")var result:List<Any>) {

}