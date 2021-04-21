package ru.flicksbox.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.flicksbox.App

class CookieCacherInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.headers(SET_COOKIE_HEADER).isNotEmpty()) {
            val context = App.appContext()
            val sp = context.getSharedPreferences(AUTH_SHARED_PREFERENCES, MODE_PRIVATE).edit()
            sp.putString(KEY_COOKIE, parseCookie(response.headers(SET_COOKIE_HEADER)))
            sp.apply()
        }

        if (response.headers(CSRF_HEADER).isNotEmpty()) {
            val context = App.appContext()
            val sp = context.getSharedPreferences(AUTH_SHARED_PREFERENCES, MODE_PRIVATE).edit()
            sp.putString(KEY_CSRF, response.headers(CSRF_HEADER)[0])
            sp.apply()
        }

        return response
    }

    private fun parseCookie(cookie: List<String>): String {
        //cookie is presented by 1 string containing many separated by ";" keys and values,
        //so we need to take the first one, containing auth token
        return cookie[0].substring(0, cookie[0].indexOf(';'))
    }
}