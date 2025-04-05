package com.app.assessment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.assessment.model.LoginResponse
import com.app.assessment.viewmodel.LoginViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private var edtUsername: EditText? = null
    private var edtPassword: EditText? = null
    private var loginViewModel: LoginViewModel? = null


    @JvmField
    @Inject
    var encryptedPrefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        edtUsername = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener { loginUser() }

        loginViewModel!!.getTokenLiveData()
            .observe(this) { data: LoginResponse? ->
                if (data != null) {
                    Log.d("TAG", "onCreate: Login " + Gson().toJson(data))
                    encryptedPrefs!!.edit().putString("auth_token", data.token).apply()
                    Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT)
                        .show()
                    // Move to next screen
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }

        // Check if biometric authentication is available
        val token = encryptedPrefs!!.getString("auth_token", null)
        if (isBiometricAvailable &&  token != null) {
            authenticateWithBiometrics()

        }
    }

    private fun loginUser() {
        val username = edtUsername!!.text.toString().trim { it <= ' ' }
        val password = edtPassword!!.text.toString().trim { it <= ' ' }
        loginViewModel!!.login(username, password)
    }

    private val isBiometricAvailable: Boolean
        get() {
            val biometricManager = BiometricManager.from(this)
            return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
        }

    private fun authenticateWithBiometrics() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    // Retrieve token and log in automatically
                    val token = encryptedPrefs!!.getString("auth_token", null)
                    if (token != null) {
                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                        // Move to next screen
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "No saved token found. Please login manually.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        this@LoginActivity,
                        "Biometric Authentication Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        val promptInfo = PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Use fingerprint to log in")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
