package ru.flicksbox.user.presentation

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import ru.flicksbox.App
import ru.flicksbox.R
import ru.flicksbox.data.Data
import ru.flicksbox.utils.notifyError


private const val NICKNAME_INPUT = "username_input"
private const val EMAIL_INPUT = "email_input"
private const val OLD_PASSWORD_INPUT = "old_password_input"
private const val NEW_PASSWORD_INPUT = "new_password_input"
private const val REPEATED_PASSWORD_INPUT = "repeated_password_input"

class ProfileActivity : AppCompatActivity() {
    lateinit var changeDataBtn: Button
    lateinit var changePasswordBtn: Button
    lateinit var nicknameInput: EditText
    lateinit var emailInput: EditText
    lateinit var oldPasswordInput: EditText
    lateinit var newPasswordInput: EditText
    lateinit var repeatPasswordInput: EditText
    lateinit var nicknameLabel: TextView
    lateinit var emailLabel: TextView
    lateinit var avatar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_layout)

        nicknameInput = findViewById(R.id.profile_input_nickname)
        emailInput = findViewById(R.id.profile_input_email)
        oldPasswordInput = findViewById(R.id.profile_input_old_password)
        newPasswordInput = findViewById(R.id.profile_input_new_password)
        repeatPasswordInput = findViewById(R.id.profile_input_repeat_password)
        changeDataBtn = findViewById(R.id.profile_btn_change_data)
        changePasswordBtn = findViewById(R.id.profile_btn_change_password)
        nicknameLabel = findViewById(R.id.profile_label_nickname_val)
        emailLabel = findViewById(R.id.profile_label_email_val)
        avatar = findViewById(R.id.profile_avatar)

        restoreState(savedInstanceState)

        App.userInteractor.getUser()
            .flowOn(Dispatchers.IO)
            .onEach { user ->
                when (user) {
                    is Data.Content -> {
                        this.runOnUiThread(java.lang.Runnable {
                            nicknameLabel.setText(user.content.nickname)
                        })
                        this.runOnUiThread(java.lang.Runnable {
                            emailLabel.setText(user.content.email)
                        })
                    }
                }
            }
            .launchIn(GlobalScope)

        changeDataBtn.setOnClickListener {
            handleSaveDataChanges()
        }

        changePasswordBtn.setOnClickListener {
            handleSavePasswordChanges()
        }
    }

    private fun handleSaveDataChanges() {
        val nickname = nicknameInput.text.toString()
        val email = emailInput.text.toString()
        if (nickname.isEmpty() && email.isEmpty()) return

        this.runOnUiThread(java.lang.Runnable {
            App.userInteractor.updateUserInfo(
                nickname,
                email
            )
                .flowOn(Dispatchers.IO)
                .onEach { user ->
                    when (user) {
                        is Data.Error -> runOnUiThread { notifyError(user.throwable, this) }
                        is Data.Loading -> Log.d("HERE", user.toString())
                        is Data.Content -> {
                            runOnUiThread { Toast.makeText(this, R.string.profile_data_changed, Toast.LENGTH_SHORT).show() }

                            if (nickname.isNotEmpty()) this.runOnUiThread(java.lang.Runnable {
                                nicknameLabel.setText(user.content.nickname)
                            })
                            if (email.isNotEmpty()) this.runOnUiThread(java.lang.Runnable {
                                emailLabel.setText(user.content.email)
                            })
                            Log.d("HERE", user.toString())
                        }
                    }
                }
                .launchIn(GlobalScope)
        })

    }

    private fun handleSavePasswordChanges() {
        App.userInteractor.updatePassword(
            oldPasswordInput.text.toString(),
            newPasswordInput.text.toString(),
            repeatPasswordInput.text.toString()
        )
            .flowOn(Dispatchers.IO)
            .onEach { user ->
                when (user) {
                    is Data.Error -> runOnUiThread { notifyError(user.throwable, this) }
                    is Data.Loading -> Log.d("HERE", user.toString())
                    is Data.Content -> runOnUiThread { Toast.makeText(this, R.string.profile_password_changed, Toast.LENGTH_SHORT).show() }
                }
            }
            .launchIn(GlobalScope)
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            nicknameInput.setText(savedInstanceState.getString(NICKNAME_INPUT))
            emailInput.setText(savedInstanceState.getString(EMAIL_INPUT))
            oldPasswordInput.setText(savedInstanceState.getString(OLD_PASSWORD_INPUT))
            newPasswordInput.setText(savedInstanceState.getString(NEW_PASSWORD_INPUT))
            repeatPasswordInput.setText(savedInstanceState.getString(REPEATED_PASSWORD_INPUT))
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putString(NICKNAME_INPUT, nicknameInput.text.toString())
        outState.putString(EMAIL_INPUT, emailInput.text.toString())
        outState.putString(OLD_PASSWORD_INPUT, oldPasswordInput.text.toString())
        outState.putString(NEW_PASSWORD_INPUT, newPasswordInput.text.toString())
        outState.putString(REPEATED_PASSWORD_INPUT, repeatPasswordInput.text.toString())
        super.onSaveInstanceState(outState, outPersistentState)
    }
}