package com.example.animations

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.animations.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
const val ACTION_COLOR = "msud.cs3013.ACTION_COLOR"
private const val COLOR_REQUEST_CODE = 0

class MainActivity : AppCompatActivity() {
    private var cb: ChalkBoard? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        cb = ChalkBoard(this)                   //Attach ChalkBoard to the activity
        backgroundLayout.addView(cb)
        fab.setOnClickListener { view ->
            cb!!.wander()                       //when FAB clicked, do animation in ChalkBoard
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Set which animation to perform on next Button click
        val id = item.itemId
        when (id) {
            raw_animation -> {
                cb!!.setStyle(ChalkBoard.RAW)
                return true
            }
            obj_animation -> {
                cb!!.setStyle(ChalkBoard.ANIMATOR)
                return true
            }
            accelorate_animation -> {
                cb!!.setStyle(ChalkBoard.ACCELERATOR)
                return true
            }
            decelorate_animation -> {
                cb!!.setStyle(ChalkBoard.DECELERATE)
                return true
            }
            bounce_animation -> {
                cb!!.setStyle(ChalkBoard.BOUNCE)
                return true
            }
            rotate_animation -> {
                cb!!.setStyle(ChalkBoard.ROTATE)
                return true
            }
            moverotate_animation -> {
                cb!!.setStyle(ChalkBoard.MOVE_ROTATE)
                return true
            }
            color_animation -> {
                cb!!.setStyle(ChalkBoard.COLOR_ACC)
                return true
            }
            movecolor_animation -> {
                cb!!.setStyle(ChalkBoard.MOVE_RECOLOR)
                return true
            }
            moverotatecolor_animation -> {
                cb!!.setStyle(ChalkBoard.MOVE_ROTATE_RECOLOR)
                return true
            }
            blink -> {
                cb!!.setStyle(ChalkBoard.BLINK)
                return true
            }
            grow_height -> {
                cb!!.setStyle(ChalkBoard.GROW_HEIGHT)
                return true
            }
            load_color -> {
                val sendIntent = Intent().apply{
                    action = ACTION_COLOR
                    type = "text/plain"
                    this.putExtra("Request Code", COLOR_REQUEST_CODE)

                }
                if (sendIntent.resolveActivity(packageManager) != null){
                    startActivityForResult(sendIntent, COLOR_REQUEST_CODE)
                    onActivityResult(COLOR_REQUEST_CODE, Activity.RESULT_OK, sendIntent)
                }

            }
            action_settings -> {
            }
            else -> {
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == COLOR_REQUEST_CODE && resultCode == RESULT_OK){
            if (data != null) {
                val tempString = data.getStringExtra("colorText")
                println(tempString)
                Toast.makeText(this, tempString, Toast.LENGTH_SHORT).show()
                val tempArray = tempString?.split(" ")
                Toast.makeText(this, tempArray.toString(), Toast.LENGTH_SHORT).show()
                if(tempArray != null) {
                    val red = tempArray[0].toInt()
                    val green = tempArray[1].toInt()
                    val blue = tempArray[2].toInt()
                    cb!!.changeColor(red, green, blue)
                }
            }

        }
    }

}
