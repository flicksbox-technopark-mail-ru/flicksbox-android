package ru.flicksbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.flicksbox.user.data.UserRepositoryImpl
import ru.flicksbox.user.data.UserService
import ru.flicksbox.user.domain.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.flicksbox.data.ApiError
import ru.flicksbox.data.Data
import ru.flicksbox.movie.data.MovieRepositoryImpl
import ru.flicksbox.movie.data.MovieService
import ru.flicksbox.movie.domain.MovieRepository
import ru.flicksbox.network.NetworkServiceFactoryImpl

class MainActivity : AppCompatActivity() {
    lateinit var userRepo: UserRepository
    lateinit var movieRepo: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val networkService = NetworkServiceFactoryImpl()
        val userService = networkService.createService(UserService::class.java)
        val repoImpl = UserRepositoryImpl(userService)
        userRepo = repoImpl

        val movieService = networkService.createService(MovieService::class.java)
        val movieRepoImpl = MovieRepositoryImpl(movieService)
        movieRepo = movieRepoImpl
    }

    override fun onStop() {
        super.onStop()
        userRepo.getUser("")
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it) {
                    is Data.Error -> Log.d("HERE", (it.throwable as ApiError).apiMessage)
                    is Data.Loading -> Log.d("HERE", it.toString())
                    is Data.Content -> Log.d("HERE", it.toString())
                }
            }.launchIn(CoroutineScope(Dispatchers.Main))

        movieRepo.getTopMovies(2, 0)
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it) {
                    is Data.Error -> Log.d("HERE", it.toString())
                    is Data.Loading -> Log.d("HERE", it.toString())
                    is Data.Content -> Log.d("HERE", it.toString())
                }
            }.launchIn(CoroutineScope(Dispatchers.Main))
    }
}