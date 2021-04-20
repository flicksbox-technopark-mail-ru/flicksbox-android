package ru.flicksbox

import android.app.Application
import android.content.Context
import ru.flicksbox.movie.data.MovieRepositoryImpl
import ru.flicksbox.movie.data.MovieService
import ru.flicksbox.network.NetworkServiceFactory
import ru.flicksbox.network.NetworkServiceFactoryImpl
import ru.flicksbox.user.data.UserRepositoryImpl
import ru.flicksbox.user.data.UserService
import ru.flicksbox.user.domain.UserInteractorImpl
import ru.flicksbox.user.presentation.UserInteractor

class App : Application() {
    companion object {
        private lateinit var instance: App
        lateinit var networkServiceFactory: NetworkServiceFactory
        lateinit var userInteractor: UserInteractor

        fun appContext(): Context {
            return instance
        }
    }

    private fun initUserInteractor() {
        val userService = networkServiceFactory.createService(UserService::class.java)
        val userRepository = UserRepositoryImpl(userService)
        userInteractor = UserInteractorImpl(userRepository)
    }

    override fun onCreate() {
        instance = this
        networkServiceFactory = NetworkServiceFactoryImpl()
        initUserInteractor()
        super.onCreate()
    }
}