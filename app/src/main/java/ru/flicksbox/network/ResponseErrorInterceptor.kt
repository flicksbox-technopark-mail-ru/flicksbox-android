package ru.flicksbox.network

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.ResponseBody
import ru.flicksbox.data.ApiException

class ResponseErrorInterceptor(private val gson: Gson) : Interceptor {
    private inline fun <reified T> convertToError(body: ResponseBody): T {
        return gson.fromJson(body.string(), T::class.java)
    }

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (!response.isSuccessful) {
            response.body?.let { responseBody ->
                val result = convertToError<BaseDTO<Unit>>(responseBody)
                if (result.error != null)
                    throw ApiException(
                        result.error.code,
                        result.error.message,
                        result.error.userMessage,
                    )
            }
        }
        return response
    }
}

