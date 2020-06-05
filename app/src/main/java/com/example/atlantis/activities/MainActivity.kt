package com.example.atlantis.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.atlantis.R
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.common.SignInButton
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception


class MainActivity : AppCompatActivity()
{
    companion object
    {
        private const val TAG = "MainActivity"
        const val RC_SIGN_IN = 0
    }

    private lateinit var fireStoreDataBase: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    private lateinit var signInButton: SignInButton

    private var mainActivityBinding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        // Hide action bar just in case login screen appears first
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        authStateListener = FirebaseAuth.AuthStateListener {firebaseAuth -> initializeAuthStateListener(firebaseAuth)}

        firebaseAuth.addAuthStateListener(authStateListener)
    }

    private var called = false
    private fun initializeAuthStateListener(firebaseAuth: FirebaseAuth)
    {
        val user = firebaseAuth.currentUser
            // If we have a user already logged in, start the main fragment
            if (user != null && !called){startMainFragment(); called = true}
            // If no user is logged in, open login page
            else{startLogInPage()}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                Toast.makeText(this, "Signed In!", Toast.LENGTH_SHORT).show()
            }
            else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show()
                finish()
            }

        }
    }

    private fun startLogInPage()
    {
        setContentView(R.layout.fragment_login)
        signInButton = findViewById(R.id.google_button)

        // Xml data wouldn't transfer so the button text is brute forced here.
        // Pretty messy but it gets the job done in one line.
        (signInButton.getChildAt(0) as TextView).text = getString(R.string.sign_in_with_google)

        // Launch google login Activity
        signInButton.setOnClickListener {
                    startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                            arrayListOf(
                                AuthUI.IdpConfig.GoogleBuilder().build()
                            )
                        )
                        .build(),
                    RC_SIGN_IN)
                }
    }

    private fun startMainFragment()
    {
        setContentView(R.layout.activity_main)
        supportActionBar?.show()
    }
}
