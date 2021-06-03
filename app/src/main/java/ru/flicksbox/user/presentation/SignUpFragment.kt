package ru.flicksbox.user.presentation

import android.os.Bundle
import android.transition.TransitionInflater
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
import ru.flicksbox.utils.hideKeyboardFrom
import ru.flicksbox.utils.notifyError

private const val USERNAME_INPUT = "username_input"
private const val EMAIL_INPUT = "email_input"
private const val PASSWORD_INPUT = "password_input"
private const val REPEATED_PASSWORD_INPUT = "repeated_password_input"

class SignUpFragment : Fragment() {
    lateinit var submitButton: Button
    lateinit var usernameInput: EditText
    lateinit var emailInput: EditText
    lateinit var passwordInput: EditText
    lateinit var repeatedPasswordInput: EditText
    lateinit var loginButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.fade_in)
        exitTransition = inflater.inflateTransition(R.transition.fade_out)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        submitButton = view.findViewById(R.id.registration_Button_register)
        usernameInput = view.findViewById(R.id.registration_EditText_username)
        emailInput = view.findViewById(R.id.registration_EditText_email)
        passwordInput = view.findViewById(R.id.registration_EditText_password)
        repeatedPasswordInput = view.findViewById(R.id.registration_EditText_repeatPassword)
        loginButton = view.findViewById(R.id.registration_TextView_logIn)

        restoreState(savedInstanceState)

        submitButton.setOnClickListener {
            handleSumbitButtonClick()
        }

        loginButton.setOnClickListener {
            handleLoginButtonClick()
        }

        return view
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
                        activity?.runOnUiThread { notifyError(user.throwable, requireContext()) }
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
        view?.let { view -> context?.let { context -> hideKeyboardFrom(context, view) } }
    }

    private fun handleLoginButtonClick() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.profile_layout, LoginFragment())
            ?.addToBackStack(null)?.commit()
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            usernameInput.setText(savedInstanceState.getString(USERNAME_INPUT))
            emailInput.setText(savedInstanceState.getString(EMAIL_INPUT))
            passwordInput.setText(savedInstanceState.getString(PASSWORD_INPUT))
            repeatedPasswordInput.setText(savedInstanceState.getString(REPEATED_PASSWORD_INPUT))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(USERNAME_INPUT, usernameInput.text.toString())
        outState.putString(EMAIL_INPUT, emailInput.text.toString())
        outState.putString(PASSWORD_INPUT, passwordInput.text.toString())
        outState.putString(REPEATED_PASSWORD_INPUT, repeatedPasswordInput.text.toString())
        super.onSaveInstanceState(outState)
    }
}