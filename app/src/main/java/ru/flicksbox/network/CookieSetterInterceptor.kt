package ru.flicksbox.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.flicksbox.App

class CookieSetterInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val sp = App.appContext()
            .getSharedPreferences(AUTH_SHARED_PREFERENCES, MODE_PRIVATE)

        val cookie = sp.getString(KEY_COOKIE, "")
        if (cookie != null) {
            if (cookie.isNotBlank()) {
                request.addHeader(COOKIE_HEADER, cookie)
            }
        }

        val csrf = sp.getString(KEY_CSRF, "")
        if (csrf != null) {
            if (csrf.isNotBlank()) {
                request.addHeader(CSRF_HEADER, csrf)
            }
        }

        return chain.proceed(request.build())
    }

}