package ru.flicksbox.user.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.flicksbox.App
import ru.flicksbox.R
import ru.flicksbox.content.main.ContentActivity
import ru.flicksbox.data.*
import ru.flicksbox.utils.notifyError

private const val USERNAME_INPUT = "username_input"
private const val EMAIL_INPUT = "email_input"
private const val PASSWORD_INPUT = "password_input"
private const val REPEATED_PASSWORD_INPUT = "repeated_password_input"

class SignupActivity : AppCompatActivity() {
    lateinit var submitButton: Button
    lateinit var usernameInput: EditText
    lateinit var emailInput: EditText
    lateinit var passwordInput: EditText
    lateinit var repeatedPasswordInput: EditText
    lateinit var loginButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        submitButton = findViewById(R.id.registration_Button_register)
        usernameInput = findViewById(R.id.registration_EditText_username)
        emailInput = findViewById(R.id.registration_EditText_email)
        passwordInput = findViewById(R.id.registration_EditText_password)
        repeatedPasswordInput = findViewById(R.id.registration_EditText_repeatPassword)
        loginButton = findViewById(R.id.registration_TextView_logIn)

        restoreState(savedInstanceState)

        submitButton.setOnClickListener {
            handleSumbitButtonClick()
        }

        loginButton.setOnClickListener {
            handleLoginButtonClick()
        }
    }

    private fun handleSumbitButtonClick() {
        App.userInteractor.signup(
            usernameInput.text.toString(),
            emailInput.text.toString(),
            passwordInput.text.toString(),
            repeatedPasswordInput.text.toString()
        )
            .flowOn(Dispatchers.IO)
            .onEach { user ->
                when (user) {
                    is Data.Error -> {
                        runOnUiThread { notifyError(user.throwable, this) }
                    }
                    is Data.Loading -> Log.d("HERE", user.toString())
                    is Data.Content -> {
                        Log.d("HERE", user.toString())
                        val intent = Intent(this, ContentActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            .launchIn(GlobalScope)
    }

    private fun handleLoginButtonClick() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            usernameInput.setText(savedInstanceState.getString(USERNAME_INPUT))
            emailInput.setText(savedInstanceState.getString(EMAIL_INPUT))
            passwordInput.setText(savedInstanceState.getString(PASSWORD_INPUT))
            repeatedPasswordInput.setText(savedInstanceState.getString(REPEATED_PASSWORD_INPUT))
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putString(USERNAME_INPUT, usernameInput.text.toString())
        outState.putString(EMAIL_INPUT, emailInput.text.toString())
        outState.putString(PASSWORD_INPUT, passwordInput.text.toString())
        outState.putString(REPEATED_PASSWORD_INPUT, repeatedPasswordInput.text.toString())
        super.onSaveInstanceState(outState, outPersistentState)
    }
}