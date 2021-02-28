package com.example.retrofit_example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
            server.getRequest("Bearer "+currentuser.access_token,"2yIBG0kMlHBGngM6I02L").enqueue(object:Callback<GetOne>{
                override fun onFailure(call: Call<GetOne>, t: Throwable) {  //object로 받아옴. 서버에서 받은 object모델과 맞지 않으면 실패함수로 빠짐
                    Log.e("태그","통신 아예 실패")
                }

                override fun onResponse(call: Call<GetOne>, response: Response<GetOne>) {
                    if(response.isSuccessful){                  //여기서 response.body()는 baby객체인 것이고, .result를 붙이면 String상태의? json데이터 이다. //즉 jsonObject의 인자는 String값으로 바뀐 json데이터가 와야함.\
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
              .enqueue(object : Callback<GetAll> {
                  override fun onFailure(
                      call: Call<GetAll>,
                      t: Throwable
                  ) {
                      Log.e("태그", "통신 아예 실패")
                  }
                  override fun onResponse(call: Call<GetAll>, response: Response<GetAll>) {
                      if (response.isSuccessful) {

                          val jsonArray = JSONArray(response.body()?.result.toString())  //(response.body()?.result.toString()은 대괄호([])로 시작해서 여러 jsonobject있는 String임
                          //간단히 설명하면 JSONObject는 중괄호({})로 시작한 애들로 key/value 의 형태를 가지고 있는 애들이다.
                          //JSONArray는 대괄호([])로 시작하는 애들로, JSONObject값들을 가지고 있음.

                          for (i in 0..jsonArray.length() - 1) {
                              val iObject = jsonArray.getJSONObject(i)
                              Log.e("태그","모두조회 response.body()내용:"+response.body().toString())
                              Log.e("태그-모두조회성공", "사용자이름:" + iObject?.getString("name") + ", 사용자 deactivated" + iObject.getBoolean("deactivated"))
                          }
                      } else {
                          Log.e("태그", "모두조회 실패:" + response.body().toString() + "errorbody: " + response.errorBody()
                          )
                      }
                  }
              })
      }

        //이용자 logs관련 get기능-조회
        button_log.setOnClickListener {
            server.getlogRequest("Bearer "+currentuser.access_token,"5jmn9ZRZgiEPhyiJoYta").enqueue(object:Callback<GetOne>{
                override fun onFailure(call: Call<GetOne>, t: Throwable) {  //object로 받아옴. 서버에서 받은 object모델과 맞지 않으면 실패함수로 빠짐
                    Log.e("태그","통신 아예 실패")
                }

                override fun onResponse(call: Call<GetOne>, response: Response<GetOne>) {
                    if(response.isSuccessful){
                        val jsonObject = JSONObject(response.body()!!.result)

                        Log.e("태그","log조회성공:"+response.body()?.success+", 결과값: "+response.body()!!.result+"이용자 cnt_id: "+jsonObject.getString("cnt")+
                            ", time:"+jsonObject.getString("time")+", inner_opened: "+jsonObject.get("inner_opened")
                                +", inner_new: "+jsonObject.get("inner_new"))
                    }else {
                        Log.e("태그","조회실패:"+response.body().toString()+"errorbody: "+response.errorBody())
                    }
                }
            })
        }

        //이용자 log-추가
        var log = Log("OA7KtWMhycQFuG9k6Bys","2021-01-01 09:00", 1, 1, 2, 2, "테스트")
        button_logAdd.setOnClickListener {
            server.addlogResquest("Bearer "+currentuser.access_token, log).enqueue(object :Callback<success>{
                override fun onFailure(call: Call<success>, t: Throwable) {
                    Log.e("태그: ", "통신 아예 실패")
                }
                override fun onResponse(call: Call<success>, response: Response<success>) {
                    if(response.isSuccessful){
                        Log.e("태그   성공: ",response.body()?.succeed.toString())
                    }else{
                        Log.e("태그   실패: ",response.body()?.succeed.toString())
                    }
                }
            })
        }


        //이용자 추가  POST기능-cnt추가
        var cnt = Cnt("봄날",false,"2층 c실","이용자a","봄날 테잎","2021-01-01")
        button_AddCnt.setOnClickListener {
            server.addCntResquest("Bearer "+currentuser.access_token, cnt).enqueue(object :Callback<success>{
                override fun onFailure(call: Call<success>, t: Throwable) {
                }
                override fun onResponse(call: Call<success>, response: Response<success>) {
                    if(response.isSuccessful){
                        Log.e("태그   성공: ",response.body()?.succeed.toString())
                    }else{
                        Log.e("태그   실패: ",response.body()?.succeed.toString())
                    }
                }
            })
        }

        //이용자 삭제 delete기능-cnt삭제
        button_deleteCnt.setOnClickListener {
            server.deleteCntRequest("Bearer "+currentuser.access_token, "SPVBcoLmFtZ8OJfO9F0V").enqueue(object :Callback<success>{
                override fun onFailure(call: Call<success>, t: Throwable) {
                }
                override fun onResponse(call: Call<success>, response: Response<success>) {
                    if(response.isSuccessful){
                        Log.e("태그   이용자 삭제성공: ",response.body()?.succeed.toString())
                    }else{
                        Log.e("태그   이용자 삭제실패: ",response.body()?.succeed.toString())
                    }
                }
            })
        }















        /*
        //이용자별 전체 로그 조회
        button_getAllLog.setOnClickListener {
            server.getAllLogRequest("Bearer "+currentuser.access_token,"OA7KtWMhycQFuG9k6Bys").enqueue(object:Callback<GetAll>{
                override fun onFailure(call: Call<GetAll>, t: Throwable) {  //object로 받아옴. 서버에서 받은 object모델과 맞지 않으면 실패함수로 빠짐
                    Log.e("태그","통신 아예 실패")
                }

                override fun onResponse(call: Call<GetAll>, response: Response<GetAll>) {
                    if(response.isSuccessful){
                        val jsonArray = JSONArray(response.body()?.result.toString())

                        for (i in 0..jsonArray.length() - 1) {
                            val iObject = jsonArray.getJSONObject(i)
                            Log.e("태그","이용자별 전체 로그조회,  만든사용자:"+iObject.getString("comment")+"inner_new: "+iObject.getInt("inner_new"))
                            Log.e("전체 로그 조회성공", "")
                        }
                    }else {
                        Log.e("태그" ,  "전체 로그 조회실패"+response.body().toString())
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


