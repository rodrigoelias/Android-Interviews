package com.rodrigoelias.score

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import com.rodrigoelias.score.data.CreditReport
import com.rodrigoelias.score.info.ScoreInfoViewModel
import kotlinx.android.synthetic.main.activity_main.pieView
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.activity_main.tv_error_message as errorMessageTextView
import kotlinx.android.synthetic.main.activity_main.tv_score_info as scoreTextView

class MainActivity : AppCompatActivity() {

    private lateinit var scoreVm: ScoreInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribe()
    }

    private fun subscribe() {
        scoreVm= ViewModelProviders.of(this)
                .get(ScoreInfoViewModel::class.java)

        scoreVm.list.observe(this, Observer { data ->
            if( data != null ) {
                updatePieView(data)
            }
        })

        scoreVm.status.observe(this, Observer { newStatus ->
            when (newStatus) {
                ScoreInfoViewModel.Status.STARTED -> {
                    scoreTextView.visibility = View.GONE
                    errorMessageTextView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    pieView.visibility = View.GONE
                }
                ScoreInfoViewModel.Status.FAILED -> {
                    scoreTextView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    pieView.visibility = View.GONE
                }
                ScoreInfoViewModel.Status.SUCCESS -> {
                    errorMessageTextView.visibility = View.GONE
                    scoreTextView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    pieView.visibility = View.VISIBLE
                }
                else -> {
                    scoreTextView.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    errorMessageTextView.visibility = View.GONE
                    pieView.visibility = View.GONE
                }

            }
        })
    }

    private fun updatePieView(data: CreditReport) {
        scoreTextView.text = "${data.score} ${data.maxScore}"
        pieView.setInnerBackgroundColor(Color.WHITE)
        pieView.percentage = (data.score.toFloat().div(data.maxScore)).times(100)
        pieView.setInnerText("")
        scoreTextView.text = buildScoreMessage(data)
    }

    private fun buildScoreMessage(data: CreditReport) : SpannableStringBuilder{
        val builder  = SpannableStringBuilder()
        builder.append("Your credit score is ")
        builder.setSpan(RelativeSizeSpan(2f), 0, builder.length-1, 0)

        val scoreStartPosition = builder.length
        builder.append("\n${data.score} \n")
        builder.setSpan(RelativeSizeSpan(3f), scoreStartPosition, builder.length-1, 0)
        builder.setSpan(ForegroundColorSpan(Color.CYAN), scoreStartPosition, builder.length-1, 0)

        val footerStartPosition = builder.length
        builder.append("out of ${data.maxScore} ")
        builder.setSpan(RelativeSizeSpan(2f), footerStartPosition, builder.length-1, 0)

        return builder
    }
}
