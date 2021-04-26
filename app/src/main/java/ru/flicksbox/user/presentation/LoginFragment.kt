package ru.flicksbox.user.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.flicksbox.App
import ru.flicksbox.R
import ru.flicksbox.data.Data
import ru.flicksbox.utils.notifyError

private const val EMAIL_INPUT = "email_input"
private const val PASSWORD_INPUT = "password_input"

class LoginFragment : Fragment() {
    lateinit var submitButton: Button
    lateinit var emailInput: EditText
    lateinit var passwordInput: EditText
    lateinit var signupButton: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        submitButton = view.findViewById(R.id.auth_Button_login)
        emailInput = view.findViewById(R.id.auth_EditText_email)
        passwordInput = view.findViewById(R.id.auth_EditText_password)
        signupButton = view.findViewById(R.id.auth_TextView_register)

        restoreState(savedInstanceState)

        submitButton.setOnClickListener {
            handleSumbitButtonClick()
        }

        signupButton.setOnClickListener {
            handleSignupButtonClick()
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun handleSumbitButtonClick() {
        App.userInteractor.login(
            emailInput.text.toString(),
            passwordInput.text.toString(),
        )
            .flowOn(Dispatchers.IO)
            .onEach { user ->
                when (user) {
                    is Data.Error -> activity?.runOnUiThread { notifyError(user.throwable, requireContext()) }
                    is Data.Loading -> {
                        Log.d("HERE", user.toString())
                    }
                    is Data.Content -> {
                        openProfileFragment()
                    }
                }
            }
            .launchIn(GlobalScope)
    }

    private fun openProfileFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.profile_layout, ProfileFragment())
            ?.addToBackStack(null)?.commit()
    }

    private fun handleSignupButtonClick() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.profile_layout, SignUpFragment())
            ?.addToBackStack(null)?.commit()
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            emailInput.setText(savedInstanceState.getString(EMAIL_INPUT))
            passwordInput.setText(savedInstanceState.getString(PASSWORD_INPUT))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EMAIL_INPUT, emailInput.text.toString())
        outState.putString(PASSWORD_INPUT, passwordInput.text.toString())
        super.onSaveInstanceState(outState)
    }
}