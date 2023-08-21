package com.example.quizzy

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizzy.R.layout.activity_quiz_questions
import com.google.android.material.snackbar.Snackbar


class QuizQuestions : AppCompatActivity(), View.OnClickListener {
    //Its better to use view Binding
    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tvQuestion: TextView? = null
    private var imgQuestion: ImageView? = null
    private var userName: String? = null

    private var optionOne: TextView? = null
    private var optionTwo: TextView? = null
    private var optionThree: TextView? = null
    private var optionFour: TextView? = null
    private var nextBtn: Button? = null

    private var currentPosition = 1
    private var correctAnswers = 10
    private var selectedOption: Int = 0
    private var questionList: ArrayList<Question>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_quiz_questions)

        userName = intent.getStringExtra(Constants.USER_NAME)

        progressBar = findViewById(R.id.progress_bar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        imgQuestion = findViewById(R.id.img_question)
        optionOne = findViewById(R.id.tv_option_one)
        optionTwo = findViewById(R.id.tv_option_two)
        optionThree = findViewById(R.id.tv_option_three)
        optionFour = findViewById(R.id.tv_option_four)
        nextBtn = findViewById(R.id.btn_next)

        questionList = Constants.getQuestions()
        setQuestion()

        optionOne?.setOnClickListener(this)
        optionTwo?.setOnClickListener(this)
        optionThree?.setOnClickListener(this)
        optionFour?.setOnClickListener(this)
        nextBtn?.setOnClickListener(this)
    }

    private fun setQuestion() {
        defaultOptionView()
        val currentQuestion: Question = questionList!![currentPosition - 1]

        progressBar?.progress = currentPosition
        tvProgress?.text = "$currentPosition / ${progressBar?.max}"
        tvQuestion?.text = currentQuestion.question
        imgQuestion?.setImageResource(currentQuestion.img)
        optionOne?.text = currentQuestion.optionOne
        optionTwo?.text = currentQuestion.optionTwo
        optionThree?.text = currentQuestion.optionThree
        optionThree?.text = currentQuestion.optionThree
        optionFour?.text = currentQuestion.optionFour

        if (currentPosition == questionList!!.size) {
            nextBtn?.text = "Submit"
        } else {
            nextBtn?.text = " Next"
        }
    }

    private fun defaultOptionView() {
        val options = ArrayList<TextView>()

        optionOne?.let {
            options.add(0, it)
        }
        optionTwo?.let {
            options.add(1, it)
        }
        optionThree?.let {
            options.add(2, it)
        }
        optionFour?.let {
            options.add(3, it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionView()

        selectedOption = selectedOptionNum
        tv.setTextColor(Color.parseColor("#610DB5"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> optionOne?.background = ContextCompat.getDrawable(this, drawableView)
            2 -> optionTwo?.background = ContextCompat.getDrawable(this, drawableView)
            3 -> optionThree?.background = ContextCompat.getDrawable(this, drawableView)
            4 -> optionFour?.background = ContextCompat.getDrawable(this, drawableView)
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_option_one -> {
                optionOne?.let {
                    selectedOptionView(it, 1)
                }
            }

            R.id.tv_option_two -> {
                optionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }

            R.id.tv_option_three -> {
                optionThree?.let {
                    selectedOptionView(it, 3)
                }
            }

            R.id.tv_option_four -> {
                optionFour?.let {
                    selectedOptionView(it, 4)
                }
            }

            R.id.btn_next -> {
                if (selectedOption == 0) {
                    currentPosition++

                    when {
                        currentPosition <= questionList!!.size -> {
                            setQuestion()
                        }

                        else -> {
                            val intent = Intent(this,ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, userName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswers.toString())
                            startActivity(intent)
                            finish()

                        }
                    }
                } else {
                    val question = questionList?.get(currentPosition - 1)
                    if (question!!.correctAnswer != selectedOption) {
                        answerView(selectedOption, R.drawable.wrong_option_border_bg)
                        correctAnswers--
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (currentPosition == questionList!!.size) {
                        nextBtn?.text = "Finish"
                    } else {
                        nextBtn?.text = "Go to next question"
                    }
                    selectedOption = 0;
                }
            }

        }
    }
}