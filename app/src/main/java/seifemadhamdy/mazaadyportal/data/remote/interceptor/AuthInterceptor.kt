package seifemadhamdy.mazaadyportal.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import seifemadhamdy.mazaadyportal.data.remote.constants.ApiConstants

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain
                .request()
                .newBuilder()
                .addHeader(ApiConstants.HEADER_AUTH, ApiConstants.PRIVATE_KEY)
                .build()
        return chain.proceed(request)
    }
}
