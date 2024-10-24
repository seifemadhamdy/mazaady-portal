package seifemadhamdy.mazaadyportal.data.remote.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import seifemadhamdy.mazaadyportal.data.remote.constants.ApiConstants
import seifemadhamdy.mazaadyportal.data.remote.interceptor.AuthInterceptor

object RetrofitClient {
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
