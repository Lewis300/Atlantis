package com.example.atlantis.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.atlantis.R
import com.google.android.gms.common.SignInButton


class Splash : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.fragment_login)
        findViewById<SignInButton>(R.id.google_button).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.appNameView).visibility = View.INVISIBLE
        val millisDelayed = 1300L
        Handler().postDelayed(Runnable {
            startActivity(Intent(this@Splash, MainActivity::class.java))
            finish()
        }, millisDelayed)
    }
}