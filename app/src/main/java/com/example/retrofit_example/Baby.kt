package com.example.retrofit_example

import com.google.gson.annotations.SerializedName

data class Baby(@SerializedName("success")var success: Boolean, @SerializedName("result")var result:String ){

//@SerializedName("inner_product")var inner_product:String, @SerializedName("deactivated")var deactivated:String, @SerializedName("description")var description:String, @SerializedName("name")var name:String, @SerializedName("username")var outer_product:String, @SerializedName("birth")var birth:String
}