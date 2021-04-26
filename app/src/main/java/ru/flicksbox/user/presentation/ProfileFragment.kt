package ru.flicksbox.user.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.flicksbox.App
import ru.flicksbox.R
import ru.flicksbox.data.Data
import ru.flicksbox.utils.buildImageUrl
import ru.flicksbox.utils.notifyError

private const val NICKNAME_INPUT = "username_input"
private const val EMAIL_INPUT = "email_input"
private const val OLD_PASSWORD_INPUT = "old_password_input"
private const val NEW_PASSWORD_INPUT = "new_password_input"
private const val REPEATED_PASSWORD_INPUT = "repeated_password_input"

class ProfileFragment : Fragment() {
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
    lateinit var logoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        nicknameInput = view.findViewById(R.id.profile_input_nickname)
        emailInput = view.findViewById(R.id.profile_input_email)
        oldPasswordInput = view.findViewById(R.id.profile_input_old_password)
        newPasswordInput = view.findViewById(R.id.profile_input_new_password)
        repeatPasswordInput = view.findViewById(R.id.profile_input_repeat_password)
        changeDataBtn = view.findViewById(R.id.profile_btn_change_data)
        changePasswordBtn = view.findViewById(R.id.profile_btn_change_password)
        nicknameLabel = view.findViewById(R.id.profile_label_nickname_val)
        emailLabel = view.findViewById(R.id.profile_label_email_val)
        avatar = view.findViewById(R.id.profile_avatar)
        logoutButton = view.findViewById(R.id.profile_logout_button)

        restoreState(savedInstanceState)

        App.userInteractor.getUser()
            .flowOn(Dispatchers.IO)
            .onEach { user ->
                when (user) {
                    is Data.Content -> {
                        activity?.runOnUiThread {
                            nicknameLabel.text = user.content.nickname
                            emailLabel.text = user.content.email
                            if (user.content.avatar.isNotBlank()) {
                                val path = buildImageUrl(user.content.avatar)
                                Picasso
                                    .with(requireContext())
                                    .load(path)
                                    .into(avatar)
                            } else {
                                avatar.setImageResource(R.drawable.default_avatar)
                            }
                        }
                    }
                    is Data.Error -> {
                        openLoginFragment()
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

        logoutButton.setOnClickListener {
            handleLogoutButtonClick()
        }

        return view
    }

    private fun openLoginFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.profile_layout, LoginFragment())
            ?.addToBackStack(null)?.commit()
    }

    private fun handleSaveDataChanges() {
        val nickname = nicknameInput.text.toString()
        val email = emailInput.text.toString()
        if (nickname.isEmpty() && email.isEmpty()) return

        activity?.runOnUiThread(java.lang.Runnable {
            App.userInteractor.updateUserInfo(
                nickname,
                email
            )
                .flowOn(Dispatchers.IO)
                .onEach { user ->
                    when (user) {
                        is Data.Error -> activity?.runOnUiThread {
                            notifyError(
                                user.throwable,
                                requireContext()
                            )
                        }
                        is Data.Loading -> Log.d("HERE", user.toString())
                        is Data.Content -> {
                            activity?.runOnUiThread {
                                Toast.makeText(
                                    requireContext(),
                                    R.string.profile_data_changed,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            if (nickname.isNotEmpty()) activity?.runOnUiThread(java.lang.Runnable {
                                nicknameLabel.setText(user.content.nickname)
                            })
                            if (email.isNotEmpty()) activity?.runOnUiThread(java.lang.Runnable {
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
                    is Data.Error -> activity?.runOnUiThread {
                        notifyError(
                            user.throwable,
                            requireContext()
                        )
                    }
                    is Data.Loading -> Log.d("HERE", user.toString())
                    is Data.Content -> activity?.runOnUiThread {
                        Toast.makeText(
                            requireContext(),
                            R.string.profile_password_changed,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(NICKNAME_INPUT, nicknameInput.text.toString())
        outState.putString(EMAIL_INPUT, emailInput.text.toString())
        outState.putString(OLD_PASSWORD_INPUT, oldPasswordInput.text.toString())
        outState.putString(NEW_PASSWORD_INPUT, newPasswordInput.text.toString())
        outState.putString(REPEATED_PASSWORD_INPUT, repeatPasswordInput.text.toString())
        super.onSaveInstanceState(outState)
    }

    private fun handleLogoutButtonClick() {
        App.userInteractor.logout()
            .flowOn(Dispatchers.IO)
            .onEach { result ->
                when (result) {
                    is Data.Content -> openLoginFragment()
                    is Data.Error -> Log.d("HERE", result.throwable.toString())
                }
            }.launchIn(CoroutineScope(Dispatchers.Main))
    }
}