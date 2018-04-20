package com.retainmvp.retainmvpexample.firstscreen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.retainmvp.retainmvp.Presenters
import com.retainmvp.retainmvpexample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityView {
    private lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = Presenters.getPresenterForActivity(
                this,
                MainActivityPresenter.factory,
                MainActivityStateConverter(),
                this,
                savedInstanceState
        )

        counterButton.setOnClickListener {
            presenter.onBumpCounterButtonPressed()
        }
    }

    override fun setCounterValue(counterValue: Int) {
        counterTextView.text = getString(R.string.counter_value, counterValue)
    }
}
