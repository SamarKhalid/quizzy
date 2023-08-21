package com.example.quizzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class ResultActivity : AppCompatActivity() {
    private var tvUserName: TextView? = null
    private var tvScore: TextView? = null
    private var btnFinish: Button? = null
    private var currentScore: String? = null
    private var currentUserName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        currentScore = intent.getStringExtra(Constants.CORRECT_ANSWERS)
        currentUserName = intent.getStringExtra(Constants.USER_NAME)

        tvUserName = findViewById(R.id.tv_name)
        btnFinish = findViewById(R.id.btn_finish)
        tvScore = findViewById(R.id.tv_score)

        tvScore!!.text = "Your Score is $currentScore out of 10"
        tvUserName!!.text = currentUserName

        btnFinish!!.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}