package android.testcurrencies.com.api

import android.util.TypedValue
import com.skogen.coin.api.requests.LoginRequest
import com.skogen.coin.models.UserModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Koba on 26.11.2018.
 * cosmicgate97@gmail.com
 */

interface RetrofitApi {

    @Multipart
    @POST("profile")
    fun performReg(@Part name: MultipartBody.Part, @Part surname: MultipartBody.Part,
                   @Part phone: MultipartBody.Part, @Part email: MultipartBody.Part,
                   @Part password: MultipartBody.Part, @Part photo: MultipartBody.Part?): Call<UserModel>

    @POST("login")
    fun performLogin(@Body login: LoginRequest): Call<UserModel>

}