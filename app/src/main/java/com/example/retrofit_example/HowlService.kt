package com.example.retrofit_example

import retrofit2.Call
import retrofit2.http.*

//우리가 무슨 데이터를 서버에 요구했을때, 서버에서는 result라는 키값에 value를 넣어서 반환해줌.
//이때 안드스튜디오에서 그 result값을 받을수있는 모델이 있어야함. 그래서 지금 이 data class가 그 모델역할.
//data class ResponseDTO(var result:String?=null)


interface HowlService {      //서버로 오고가는 api들을 관리해주는 인터페이스임  //서버와 앱 간의 연결역할..?

    //사용자 등록
    //post는 @Field는 FormData형식으로 보냄. @Body는 json으로 서버에 보내줌.
    //@FormUrlEncoded       //밑에 함수에 field가 있다면 넣어야됨.
    @POST("api/auth/register")
    fun postResquest(@Body users: Users):Call<success>

    //로그인
    @POST("api/auth/login")
    fun loginRequest(@Body users: Users):Call<currentUser>

    //로그아웃
    @POST("api/auth/logoutc")
    fun logoutRequest(@Body currentUser: currentUser):Call<success>

    //이용자 조회
    //@Headers("Authorization:Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MTQxNjA1ODIsIm5iZiI6MTYxNDE2MDU4MiwianRpIjoiZWI3MGQ2ZTAtMTRlZC00NDdiLTg4ZDAtM2NjODc1ZDdkMjVjIiwiZXhwIjoxNjE0NzY1MzgyLCJpZGVudGl0eSI6ImFhIiwiZnJlc2giOmZhbHNlLCJ0eXBlIjoiYWNjZXNzIiwidXNlcl9jbGFpbXMiOnsibGV2ZWwiOjF9LCJjc3JmIjoiNzMyMzMwN2MtMWI4Yi00MzJhLWI3YWQtNDcwYWVkYzQ3NTNkIn0.6RVX6G_S-0AKm_ex-obiiSfgNOFTQq6RPfePJVAqjZE")
    @GET("api/cnts/{cnt_id}")
    fun getRequest(@Header("Authorization")authorization:String, @Path("cnt_id") cnt_id: String): Call<GetOne>


    //이용자 모두조회
    //@Headers("Authorization:Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MTQxNjA1ODIsIm5iZiI6MTYxNDE2MDU4MiwianRpIjoiZWI3MGQ2ZTAtMTRlZC00NDdiLTg4ZDAtM2NjODc1ZDdkMjVjIiwiZXhwIjoxNjE0NzY1MzgyLCJpZGVudGl0eSI6ImFhIiwiZnJlc2giOmZhbHNlLCJ0eXBlIjoiYWNjZXNzIiwidXNlcl9jbGFpbXMiOnsibGV2ZWwiOjF9LCJjc3JmIjoiNzMyMzMwN2MtMWI4Yi00MzJhLWI3YWQtNDcwYWVkYzQ3NTNkIn0.6RVX6G_S-0AKm_ex-obiiSfgNOFTQq6RPfePJVAqjZE")
    @GET("api/cnts")
    fun getAllRequest(@Header("Authorization")authorization:String): Call<GetAll>


     //logs 조회
     @GET("api/logs/{log_id}")
     fun getlogRequest(@Header("Authorization")authorization:String, @Path("log_id") log_id: String): Call<GetOne>

    //logs추가
    @POST("api/logs")
    fun addlogResquest(@Header("Authorization")authorization:String, @Body log: Log):Call<success>

    //이용자 추가
    @POST("api/cnts")
    fun addCntResquest(@Header("Authorization")authorization:String, @Body cnt: Cnt):Call<success>

    //이용자 삭제
    @DELETE("api/cnts/{cnt_id}")
    fun deleteCntRequest(@Header("Authorization")authorization:String,@Path("cnt_id") cnt_id: String):Call<success>











    /*
    //이용자 정보 수정
    @PATCH("api/cnts/{cnt_id}")
    fun modifiy_cnt(@Header("Authorization")authorization:String, @Body cnt: Cnt):Call<success>
     */


    /*
    //이용자별 전체 로그 조회
    @GET("api/logs/cnt/{cnt_id}")
    fun getAllLogRequest(@Header("Authorization")authorization:String, @Path("cnt_id") cnt_id: String): Call<GetAll>
     */


/*

    @GET("")
    fun getParamRequest(@Path("id")id:String):Call<ResponseDTO>




    @FormUrlEncoded       //밑에 함수에 field가 없다면 생략해도됨
    @PUT("")
    fun putRequest(@Path("id") id:String, @Field("content")content:String):Call<ResponseDTO>

    @DELETE("")
    fun deleteRequest(@Path("id")id:String):Call<ResponseDTO>

 */
}