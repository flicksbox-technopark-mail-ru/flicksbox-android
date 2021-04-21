package ru.flicksbox.user.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.flicksbox.App
import ru.flicksbox.R
import ru.flicksbox.data.*
import ru.flicksbox.utils.notifyError

private const val EMAIL_INPUT = "email_input"
private const val PASSWORD_INPUT = "password_input"

class LoginActivity : AppCompatActivity() {
    lateinit var submitButton: Button
    lateinit var emailInput: EditText
    lateinit var passwordInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth)

        submitButton = findViewById(R.id.auth_Button_login)
        emailInput = findViewById(R.id.auth_EditText_email)
        passwordInput = findViewById(R.id.auth_EditText_password)

        restoreState(savedInstanceState)

        submitButton.setOnClickListener {
            handleSumbitButtonClick()
        }
    }

    private fun handleSumbitButtonClick() {
        App.userInteractor.login(
            emailInput.text.toString(),
            passwordInput.text.toString(),
        )
            .flowOn(Dispatchers.IO)
            .onEach { user ->
                when (user) {
                    is Data.Error -> runOnUiThread { notifyError(user.throwable, this) }
                    is Data.Loading -> Log.d("HERE", user.toString()) //TODO(Create loading UI element)
                    is Data.Content -> Log.d("HERE", user.toString()) //TODO(Create )
                }
            }
            .launchIn(GlobalScope)
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            emailInput.setText(savedInstanceState.getString(EMAIL_INPUT))
            passwordInput.setText(savedInstanceState.getString(PASSWORD_INPUT))
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putString(EMAIL_INPUT, emailInput.text.toString())
        outState.putString(PASSWORD_INPUT, passwordInput.text.toString())
        super.onSaveInstanceState(outState, outPersistentState)
    }
}