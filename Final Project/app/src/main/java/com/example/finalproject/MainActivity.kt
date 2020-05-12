package com.example.finalproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.intro_screen.*
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), SensorEventListener {
    private val openURL = Intent(android.content.Intent.ACTION_VIEW)
    private lateinit var sensorManager: SensorManager
    private var lastAcceleration = 0f
    private var currentAcceleration = 0f
    private var acceleration = 0f
    private var clicks = 0
    private var running = false
    private var steps = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setLogo(R.drawable.app_icon)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        addFragment(IntroFragment, R.id.fragmentContent)
        sensorSetUp()

        clickCount?.text = getClicks().toString()
        stepCount?.text = getSteps().toString()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.sendButton) {
            when {
                HappyFragment.isAdded -> {
                    openURL.data = Uri.parse("https://www.youtube.com/watch?v=HP-MbfHFUqs")
                    startActivity(openURL) }
                HungryFragment.isAdded -> {
                    openURL.data = Uri.parse("https://www.youtube.com/watch?v=ZcJjMnHoIBI")
                    startActivity(openURL) }
                MadFragment.isAdded -> {
                    openURL.data = Uri.parse("https://www.youtube.com/watch?v=L-iepu3EtyE")
                    startActivity(openURL) }
                RunFragment.isAdded -> {
                    openURL.data = Uri.parse("https://www.youtube.com/watch?v=btPJPFnesV4")
                    startActivity(openURL) }
                SadFragment.isAdded -> {
                    openURL.data = Uri.parse("https://www.youtube.com/watch?v=VYOjWnS4cMY")
                    startActivity(openURL) }
                TiredFragment.isAdded -> {
                    openURL.data = Uri.parse("https://www.youtube.com/watch?v=BVupSeAWIaw")
                    startActivity(openURL) }
                else -> {
                    openURL.data = Uri.parse("https://www.youtube.com/watch?v=o1tj2zJ2Wvg")
                    startActivity(openURL)
                }
            }
        } else
            Toast.makeText(this, "Oh no", Toast.LENGTH_SHORT).show()

        return super.onOptionsItemSelected(item)
    }

    fun changeFragment(view: View) {
        when (view.id) {
            R.id.happyButton -> { replaceFragment(HappyFragment, R.id.fragmentContent) }
            R.id.hungryButton -> { replaceFragment(HungryFragment, R.id.fragmentContent) }
            R.id.madButton -> { replaceFragment(MadFragment, R.id.fragmentContent) }
            R.id.sadButton -> { replaceFragment(SadFragment, R.id.fragmentContent) }
            R.id.tiredButton -> { replaceFragment(TiredFragment, R.id.fragmentContent) }

            R.id.happyLayout -> { replaceFragment(IntroFragment, R.id.fragmentContent) }
            R.id.happyView -> { replaceFragment(IntroFragment, R.id.fragmentContent) }

            R.id.hungryLayout -> { replaceFragment(IntroFragment, R.id.fragmentContent) }
            R.id.hungryView -> { replaceFragment(IntroFragment, R.id.fragmentContent) }

            R.id.madLayout -> { replaceFragment(IntroFragment, R.id.fragmentContent) }
            R.id.madView -> { replaceFragment(IntroFragment, R.id.fragmentContent) }

            R.id.sadLayout -> { replaceFragment(IntroFragment, R.id.fragmentContent) }
            R.id.sadView -> { replaceFragment(IntroFragment, R.id.fragmentContent) }

            R.id.runLayout -> { replaceFragment(IntroFragment, R.id.fragmentContent) }
            R.id.runView -> { replaceFragment(IntroFragment, R.id.fragmentContent) }

            R.id.tiredLayout -> { replaceFragment(IntroFragment, R.id.fragmentContent) }
            R.id.tiredView -> { replaceFragment(IntroFragment, R.id.fragmentContent) }
        }
        clicks++
        setClicks(clicks, steps)
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction. () -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    private fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    private fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
    }

    private fun sensorSetUp() {
        this.sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        this.sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        if (this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            Log.v("success", "yes")
        }
        else {
            Log.v("failure", "no sensor found")
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            Toast.makeText(this, "no step counter", Toast.LENGTH_SHORT).show()
        }
        else {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) { }

    override fun onSensorChanged(event: SensorEvent?) {
        val a = event!!.values[0]
        val b = event.values[1]
        val c = event.values[2]

        lastAcceleration = currentAcceleration
        currentAcceleration = sqrt((a * a + b * b + c * c))
        val delta = currentAcceleration - lastAcceleration
        acceleration = acceleration * 0.9f + delta
        when {
            acceleration > 10 -> {
                replaceFragment(RunFragment, R.id.fragmentContent)
            }
            running -> {
                stepCount?.text = steps.toString()
                setClicks(clicks, steps)
            }
            else -> { return }
        }
    }

    private fun setClicks(clicks: Int, steps: Float) {
        val preferences = getSharedPreferences("Delusionallyhere", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt("clicks", clicks)
        editor.putFloat("steps", steps)
        editor.apply()
    }

    private fun getClicks(): Int {
        val preferences = getSharedPreferences("Delusionallyhere", Context.MODE_PRIVATE)
        return preferences.getInt("clicks", 0)
    }

    private fun getSteps(): Float {
        val preferences = getSharedPreferences("Delusionallyhere", Context.MODE_PRIVATE)
        return preferences.getFloat("steps", 0f)
    }

    companion object {
        private val IntroFragment = IntroFragment()
        private val HappyFragment = HappyFragment()
        private val HungryFragment = HungryFragment()
        private val MadFragment = MadFragment()
        private val SadFragment = SadFragment()
        private val RunFragment = RunFragment()
        private val TiredFragment = TiredFragment()
    }
}

