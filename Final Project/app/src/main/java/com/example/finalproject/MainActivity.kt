package com.example.finalproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val ACTION_COLOR = "msud.cs3013.ACTION_COLOR"
private const val COLOR_REQUEST_CODE = 0

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        //supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.setLogo(R.drawable.app_icon)
        supportActionBar?.setDisplayUseLogoEnabled(true)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.sendButton) {
            val sendIntent = Intent().apply{
                action = ACTION_COLOR
                type = "text/plain"
                this.putExtra("Request Code", COLOR_REQUEST_CODE)
            }
            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(sendIntent, COLOR_REQUEST_CODE)
                onActivityResult(COLOR_REQUEST_CODE, Activity.RESULT_OK, sendIntent)
            }
        }
        else
            Toast.makeText(this, "Oh no", Toast.LENGTH_SHORT).show()

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == COLOR_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                val tempString = data.getStringExtra("colorText")
                println(tempString)
                Toast.makeText(this, tempString, Toast.LENGTH_SHORT).show()
                val tempArray = tempString?.split(" ")
                Toast.makeText(this, tempArray.toString(), Toast.LENGTH_SHORT).show()
                if (tempArray != null) {
                    val red = tempArray[0].toInt()
                    val green = tempArray[1].toInt()
                    val blue = tempArray[2].toInt()

                }
            }
        }
    }

    fun changeColor(red: Int, green: Int, blue: Int) {
        currentColor =
    }
    fun changeFragment(newFragmentId: Int) {
        when(newFragmentId) {
            R.id.happyButton -> {supportFragmentManager.beginTransaction().replace(R.id.fragmentContent, HappyFragment()).commit()}
            R.id.hungryButton -> {supportFragmentManager.beginTransaction().replace(R.id.fragmentContent, HungryFragment()).commit()}
            R.id.madButton -> {supportFragmentManager.beginTransaction().replace(R.id.fragmentContent, MadFragment()).commit()}
            R.id.sadButton -> {supportFragmentManager.beginTransaction().replace(R.id.fragmentContent, SadFragment()).commit()}
            R.id.tiredButton -> {supportFragmentManager.beginTransaction().replace(R.id.fragmentContent, TiredFragment()).commit()}
        }
    }
}
