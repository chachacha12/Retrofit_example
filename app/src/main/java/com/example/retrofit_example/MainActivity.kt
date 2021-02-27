package com.example.retrofit_example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var retrofit = Retrofit.Builder()
            .baseUrl("https://diapers-dungji.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(HowlService::class.java)  //서버와 만들어둔 인터페이스를 연결시켜줌.

        lateinit var currentuser:currentUser


        //사용자 auth 관련 post기능-등록
        var users = Users("mymy","1234","my","my")
        button_post.setOnClickListener {
            server.postResquest(users).enqueue(object :Callback<success>{
                override fun onFailure(call: Call<success>, t: Throwable) {
                }
                override fun onResponse(call: Call<success>, response: Response<success>) {
                    Log.e("성공",response.body().toString())
                }
            })
        }

        //사용자 auth 관련 post기능-로그인

        var loginusers = Users("aa","1234")  //실제 만들땐 editText로 값 받기
        login.setOnClickListener {
            server.loginRequest(loginusers).enqueue(object :Callback<currentUser>{
                override fun onFailure(call: Call<currentUser>, t: Throwable) {
                    Log.e("태그","로그인 통신 아예 실패")
                }
                override fun onResponse(call: Call<currentUser>, response: Response<currentUser>) {
                    if(response.isSuccessful){
                        currentuser = currentUser(response.body()?.username.toString(),response.body()?.access_token!!)
                        Log.e("태그","로그인성공, 사용자이름:"+currentuser.username+", 토큰값: "+currentuser.access_token)
                    }else{
                        Log.e("태그","로그인실패"+response.body().toString())
                    }

                }
            })
        }

        //사용자 auth 관련 post기능-로그아웃
        logout.setOnClickListener {
            server.logoutRequest(currentuser).enqueue(object :Callback<success>{
                override fun onFailure(call: Call<success>, t: Throwable) {
                    Log.e("태그","로그아웃 실패")
                }
                override fun onResponse(call: Call<success>, response: Response<success>) {
                    Log.e("태그","로그아웃 성공"+response.body().toString())
                    currentuser.username= null.toString()
                    currentuser.access_token= null.toString()
                    Log.e("태그","토큰값:"+currentuser.access_token)
                }
            })
        }


        //이용자 cnts 관련 get기능-조회
        button_get.setOnClickListener {
            server.getRequest("Bearer "+currentuser.access_token,"2yIBG0kMlHBGngM6I02L").enqueue(object:Callback<Baby>{
                override fun onFailure(call: Call<Baby>, t: Throwable) {  //object로 받아옴. 서버에서 받은 object모델과 맞지 않으면 실패함수로 빠짐
                    Log.e("태그","통신 아예 실패")
                }

                override fun onResponse(call: Call<Baby>, response: Response<Baby>) {
                    if(response.isSuccessful){                  //여기서 response.body()는 baby객체인 것이고, .result를 붙이면 String상태의? json데이터 이다. //즉 jsonObject의 인자는 String값으로 바뀐 json데이터가 와야함.
                        val jsonObject = JSONObject(response.body()!!.result) //response.body()!!.resuls 이거는 json데이터가 String인 상태이다. 이걸 jsonobject라이브러리 이용해서 접근가능한 데이터로 파싱해준거임
                        Log.e("태그","조회성공:"+response.body()?.success+", 결과값: "+response.body()!!.result+"json파싱: "+jsonObject.getString("name")
                                +"json파싱: "+jsonObject.getString("inner_product"))
                    }else {
                        Log.e("태그","조회실패:"+response.body().toString()+"errorbody: "+response.errorBody())
                    }
                }
            })
        }


      //이용자 cnts 관련 get기능-모두 조회
      button_get_param.setOnClickListener {
          server.getAllRequest("Bearer " + currentuser.access_token)
              .enqueue(object : Callback<Babys> {
                  override fun onFailure(
                      call: Call<Babys>,
                      t: Throwable
                  ) {
                      Log.e("태그", "통신 아예 실패")
                  }

                  override fun onResponse(call: Call<Babys>, response: Response<Babys>) {
                      if (response.isSuccessful) {

                          val jsonArray = JSONArray(response.body()?.result.toString())  //(response.body()?.result.toString()은 대괄호([])로 시작해서 여러 jsonobject있는 String임
                          //간단히 설명하면 JSONObject는 중괄호({})로 시작한 애들로 key/value 의 형태를 가지고 있는 애들이다.
                          //JSONArray는 대괄호([])로 시작하는 애들로, JSONObject값들을 가지고 있음.

                          for (i in 0..jsonArray.length() - 1) {
                              val iObject = jsonArray.getJSONObject(i)
                              Log.e("태그-모두조회성공", "사용자이름:" + iObject?.getString("name") + ", 사용자 deactivated" + iObject.getBoolean("deactivated"))
                          }
                      } else {
                          Log.e("태그", "모두조회 실패:" + response.body().toString() + "errorbody: " + response.errorBody()
                          )
                      }
                  }
              })
      }




        /*
        //logs 관련 get기능-조회
        button_get.setOnClickListener {
            server.getlogRequest("Bearer "+currentuser.access_token,"2yIBG0kMlHBGngM6I02L").enqueue(object:Callback<Baby>{
                override fun onFailure(call: Call<Baby>, t: Throwable) {  //object로 받아옴. 서버에서 받은 object모델과 맞지 않으면 실패함수로 빠짐
                    Log.e("태그","통신 아예 실패")
                }

                override fun onResponse(call: Call<Baby>, response: Response<Baby>) {
                    if(response.isSuccessful){
                        Log.e("태그","조회성공:"+response.body()?.success+"결과값: "+response.body()?.result)
                    }else {
                        Log.e("태그","조회실패:"+response.body().toString()+"errorbody: "+response.errorBody())
                    }
                }
            })
        }

         */











        /*
        button_get_param.setOnClickListener {
            server.getParamRequest("").enqueue(object:Callback<ResponseDTO>{
                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response?.body().toString())
                 }
            })
        }

         */


        /*
        button_update.setOnClickListener {
            server.putRequest("","수정할 내용").enqueue(object :Callback<ResponseDTO>{
                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response?.body().toString())
                }

            })
        }

        button_delete.setOnClickListener {
            server.deleteRequest("").enqueue(object :Callback<ResponseDTO>{
                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response?.body().toString())
                }

            })
        }

         */


    }//onCrete



}


