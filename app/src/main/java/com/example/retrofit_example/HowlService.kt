package com.example.retrofit_example

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

//우리가 특정 데이터를 서버에 요구했을때, 서버에서는 result라는 키값에 value를 넣어서 반환.
//이때 안드스튜디오에서 그 result값을 받을수있는 모델이 있어야함. 그래서 만들어둔 data class가 그 모델역할.

//서버에 보내야할 값들 중, 헤더는 @Header로, 파라미터는 @Path로 , Request body는 @Body라는 annotation으로 보내주기.
//서버의 url주소가 : 다음의 값은 {}로 묶어주고, ? 다음의 값들은 지워주고 @Query로 보내주면 됨



interface HowlService {      //서버로 오고가는 api들을 관리해주는 인터페이스임  //서버와 앱 간의 연결역할

    //post는 @Field는 FormData형식으로 보냄. @Body는 json으로 서버에 보내줌.
    // @FormUrlEncoded       //밑에 함수에 field가 있다면 넣어야됨.

    //Flav -- Image를 POST요청때 사용.
    @Multipart
    @POST("admin/name")
    fun postpictures(@Part file: MultipartBody.Part):Call<Name> //file의 타입은 MultipartBody.Part. 반환값은 Name객체




    /*
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

     //logs 조회
     @GET("api/logs/{log_id}")
     fun getlogRequest(@Header("Authorization")authorization:String, @Path("log_id") log_id: String): Call<GetOne>


    //logs추가
    @POST("api/logs")
    fun addlogResquest(@Header("Authorization")authorization:String, @Body log: Log):Call<success>


    //이용자 추가
    @POST("api/cnts")
    fun addCntResquest(@Header("Authorization")authorization:String, @Body cnt: Cnt):Call<success>


    //이용자Log 정보 수정
    @PATCH("api/logs/{log_id}")
    fun modifiy_log(@Header("Authorization")authorization:String,@Path("log_id") log_id: String, @Body log: Log) :Call<success>


    //특정로그 삭제
    @DELETE("api/logs/{log_id}")
    fun deleteLogRequest(@Header("Authorization")authorization:String,@Path("log_id") log_id: String):Call<success>

    //이용자별 전체 로그 조회
    @GET("api/logs/cnt/{cnt_id}")
    fun getAllLogRequest(@Header("Authorization")authorization:String, @Path("cnt_id") cnt_id: String): Call<GetAll>


    //서버의 url주소가 : 다음의 값은 {}로 묶어주고, ? 다음의 값들은 지워주고 @Query로 보내주면 됨!!!
    //이용자별 로그 리스트 조회 (페이지네이션) - 페이지0, 사이즈2를 하면 로그값 2개가 조회됨.
    @GET("api/logs/cnt/{cnt_id}")
    fun getLogListRequest(@Header("Authorization")authorization:String, @Path("cnt_id") cnt_id: String, @Query("page") page: Number, @Query("size") size: Number): Call<GetAll>


    //특정기간 이용자별 로그리스트 조회 (페이지네이션)
    @GET("api/logs/cnt/{cnt_id}")
    fun getLog_period_Request(@Header("Authorization")authorization:String, @Path("cnt_id") cnt_id: String, @Query("page") page: Number, @Query("size") size: Number, @Query("start") start: String, @Query("end") end: String): Call<GetAll>


    //특정기간 이용자별 전체 로그리스트 조회
    @GET("api/logs/cnt/{cnt_id}")
    fun getAllLog_period_Request(@Header("Authorization")authorization:String, @Path("cnt_id") cnt_id: String, @Query("start") start: String, @Query("end") end: String): Call<GetAll>


    //이용자 조회
    @GET("api/cnts/{cnt_id}")   //서버의 문서에선 : 이라고 되어있는 부분은 여기선 {}로 묶어줘야함
    fun getRequest(@Header("Authorization")authorization:String, @Path("cnt_id") cnt_id: String): Call<GetOne>


    //이용자 모두조회
    @GET("api/cnts")
    fun getAllRequest(@Header("Authorization")authorization:String): Call<GetAll>


    //사용자 모두 조회
    @GET("api/users")
    fun getAllusers_Request(@Header("Authorization")authorization:String): Call<GetAll>


    //사용자 삭제
    @DELETE("api/users/{user_id}")
    fun deleteUserRequest(@Header("Authorization")authorization:String,@Path("user_id") user_id: String):Call<success>


    //이용자 삭제
    @DELETE("api/cnts/{cnt_id}")
    fun deleteCntRequest(@Header("Authorization")authorization:String,@Path("cnt_id") cnt_id: String):Call<success>


  //아이디 중복검사
    @GET("api/auth/exist/{username}")
    fun ID_check_Resquest(@Path("username") username:String):Call<IDcheck_Response>



    //이용자 정보 수정
    @PATCH("api/cnts/{cnt_id}")
    fun modifiy_cnt(@Header("Authorization")authorization:String,@Path("cnt_id") cnt_id: String, @Body cnt: Cnt) :Call<success>

    @GET("")
    fun getParamRequest(@Path("id")id:String):Call<ResponseDTO>




    @FormUrlEncoded       //밑에 함수에 field가 없다면 생략해도됨
    @PUT("")
    fun putRequest(@Path("id") id:String, @Field("content")content:String):Call<ResponseDTO>

    @DELETE("")
    fun deleteRequest(@Path("id")id:String):Call<ResponseDTO>

 */



}