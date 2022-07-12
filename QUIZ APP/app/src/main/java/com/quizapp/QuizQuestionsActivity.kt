package com.quizapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

// TODO (STEP 1 : Create a QuizQuestions Activity.)
// START
class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

     private var mCurrentPosition :Int=1
    private var mQuestionList :ArrayList<Question>? = null
    private var mSelectedOptionPostion :Int = 0
    private var numcorrect :Int = 0
    private var mUsername :String?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)
        mUsername = intent.getStringExtra(Constants.USER_NAME)
        mQuestionList = Constants.getQuestions()
         setQuestion()
         tv_option1.setOnClickListener(this)
        tv_option2.setOnClickListener(this)
        tv_option3.setOnClickListener(this)
        tv_option4.setOnClickListener(this)
        btn_submit.setOnClickListener(this)

        }
     private fun setQuestion() {

         val question = mQuestionList!![mCurrentPosition-1]
         defaultOptionView()
         if(mCurrentPosition == mQuestionList!!.size){
             btn_submit.text = "FINISH"
         }else{
             btn_submit.text = "SUBMIT"
         }
         progressBar.progress = mCurrentPosition
         tv_progress.text = "$mCurrentPosition"+"/"+progressBar.max
         tv_question.text = question!!.question
         iv_image.setImageResource(question.image)
         tv_option1.text = question.optionOne
         tv_option2.text = question.optionTwo
         tv_option3.text = question.optionThree
         tv_option4.text = question.optionFour
     }
       private fun defaultOptionView(){
           val options = ArrayList<TextView>()
           options.add(0,tv_option1)
           options.add(1,tv_option2)
           options.add(2,tv_option3)
           options.add(3,tv_option4)


           for (option in options){
               option.setTextColor(Color.parseColor("#7A8089"))
               option.typeface = Typeface.DEFAULT
               option.background = ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)
           }


       }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.tv_option1 ->{
                selectedOptionView(tv_option1,1)
            }
            R.id.tv_option2 ->{
                selectedOptionView(tv_option2,2)
            }
            R.id.tv_option3 ->{
                selectedOptionView(tv_option3,3)
            }
            R.id.tv_option4 ->{
                selectedOptionView(tv_option4,4)
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPostion == 0){
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, Result::class.java)
                            intent.putExtra(Constants.USER_NAME, mUsername)
                            intent.putExtra(Constants.CORRECT_ANSWERS, numcorrect)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }



                } else{
                    val question = mQuestionList?.get(mCurrentPosition -1)
                    if(question!!.correctAnswer!=mSelectedOptionPostion){
                        answerView(mSelectedOptionPostion,R.drawable.wrong_option_border_bg)
                    }else{
                        numcorrect++
                    }
                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg)
                    if(mCurrentPosition == mQuestionList!!.size){
                        btn_submit.text="FINISH"

                    }else{
                        btn_submit.text ="GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPostion=0

                }
            }
        }


    }

    private fun selectedOptionView(tv:TextView,selectedOptionNum:Int){
        defaultOptionView()
        mSelectedOptionPostion = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,R.drawable.selected_option_border_bg)
    }

    private fun answerView(answer: Int,drwableView: Int){
        when (answer){
            1->{
                tv_option1.background = ContextCompat.getDrawable(this,drwableView
                )
            }
            2 ->{
                tv_option2.background = ContextCompat.getDrawable(this,drwableView
                )

            }
            3 ->{
                tv_option3.background = ContextCompat.getDrawable(this,drwableView)

        }
            4 ->{
                tv_option4.background = ContextCompat.getDrawable(this,drwableView
                )

    }
        }

    }
}


// END