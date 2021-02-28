package com.example.retrofit_example

import com.google.gson.annotations.SerializedName

data class Log(@SerializedName("cnt") var cnt:String, @SerializedName("time") var time:String, @SerializedName("inner_opened") var inner_opened:Number,
               @SerializedName("inner_new") var inner_new:Number, @SerializedName("outer_opened") var outer_opened:Number, @SerializedName("outer_new") var outer_new:Number,
               @SerializedName("comment") var comment:String) {

}