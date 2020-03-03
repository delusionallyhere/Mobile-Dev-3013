package com.example.mobiledevtestapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val changingTV = findViewById<TextView>(R.id.textView)

        val hideButton: Button = findViewById(R.id.hideButton)
        hideButton.setOnClickListener {
           if (changingTV.visibility ==View.INVISIBLE ) {
               changingTV.visibility = View.VISIBLE
           }
            else
               changingTV.visibility = View.INVISIBLE
        }

        var colorButtonChoice = 1

        val colorButton: Button = findViewById(R.id.colorButton)
        colorButton.setOnClickListener {
            if (colorButtonChoice == 2) {
                changingTV.setBackgroundColor(resources.getColor(R.color.textViewBackground))
                colorButtonChoice = 1
            }
            else if (colorButtonChoice == 1) {
                changingTV.setBackgroundColor(resources.getColor(R.color.textViewBGChange))
                colorButtonChoice = 2
            }
        }


    }

    fun changeText(view: View) {
        textView.text =  getString(R.string.helpInfo)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true

            R.id.help -> {
                changeText(textView)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
