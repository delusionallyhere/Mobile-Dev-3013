package com.example.colorpicker

import android.content.Context
import android.content.Intent
import android.graphics.Color.red
import android.graphics.Color.rgb
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.color_picker.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

var colorArray = arrayListOf<String>()

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_main)

        this.setSupportActionBar(this.findViewById(R.id.toolbar))

        this.redSeekBar.setOnSeekBarChangeListener(this)
        this.greenSeekBar.setOnSeekBarChangeListener(this)
        this.blueSeekBar.setOnSeekBarChangeListener(this)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setLogo(R.drawable.app_icon)
        this.supportActionBar?.setDisplayUseLogoEnabled(true)

        this.redNumberView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                this@MainActivity.redSeekBar.progress = this@MainActivity.redNumberView.text.toString().toInt()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        this.greenNumberView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                this@MainActivity.greenSeekBar.progress = this@MainActivity.greenNumberView.text.toString().toInt()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        this.blueNumberView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                this@MainActivity.blueSeekBar.progress = this@MainActivity.blueNumberView.text.toString().toInt()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar) {
            this.redSeekBar -> this.redNumberView.setText(this.redSeekBar.progress.toString())
            this.greenSeekBar -> this.greenNumberView.setText(this.greenSeekBar.progress.toString())
            this.blueSeekBar -> this.blueNumberView.setText(this.blueSeekBar.progress.toString())
        }

        val newColor: Int = rgb(
            this.redNumberView.text.toString().toInt(),
            this.greenNumberView.text.toString().toInt(),
            this.blueNumberView.text.toString().toInt()
        )
        this.colorPreview.setBackgroundColor(newColor)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {   }
    override fun onStopTrackingTouch(seekBar: SeekBar?) {   }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val colorFileName = "colors.txt"
        val colorListFragment = this.supportFragmentManager.findFragmentById(R.id.colorListFrag) as ColorListFragment
        val colorData = this.findViewById<EditText>(R.id.colorEditText).text.toString()
        val id = item.itemId
        val file = File(this.filesDir.absolutePath + "/colors.txt")

        if (id == R.id.saveColor && !this.colorEditText.text.isBlank()) {
            val color = "${colorData.toLowerCase()} ${this.redNumberView.text} ${this.greenNumberView.text} ${this.blueNumberView.text}\n"
            try {
                if (file.exists()) {
                    println("EXISTS")
                    this.openFileOutput(colorFileName, Context.MODE_APPEND).use { it.write(color.toByteArray()) }
                }
                else {
                    this.openFileOutput(colorFileName, Context.MODE_PRIVATE).use { it.write(color.toByteArray()) }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            this.colorEditText.text.clear()
            colorListFragment.getSavedColors()
            colorListFragment.updateColors()
            return true
        }
        else if (id == R.id.loadColor) {
            try {
                colorListFragment.getSavedColors()
                colorListFragment.updateColors()
                Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show()

            } catch (e: FileNotFoundException) {
                Toast.makeText(this, "No Existing Colors!", Toast.LENGTH_SHORT).show()
            }
            return true
        }
        else if (id == R.id.sendButton) {
            val info = this.intent.extras
            if (info != null) {
                    if(info.containsKey("Request Code")) {
                        println("Key Inside!")
                        var returnText = this.getColorString()
                        this.finish()
                    }
            }
        }
        else
            Toast.makeText(this, "No color name entered.", Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
        }


    fun onColorListClick (view: View) {
        val tempColor = ((view.background as ColorDrawable).color)
        val red: Int = tempColor shr 16 and 0xFF
        val green: Int = tempColor shr 8 and 0xFF
        val blue: Int = tempColor shr 0 and 0xFF
        println("$red $green $blue")
        Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show()
        this.redNumberView.setText(red.toString())
        this.greenNumberView.setText(green.toString())
        this.blueNumberView.setText(blue.toString())
        this.redSeekBar.progress = red
        this.greenSeekBar.progress = green
        this.blueSeekBar.progress = blue
        this.colorPreview.setBackgroundColor(tempColor)
    }

    private fun getColorString(): String {
        val tempColor = ((this.colorPreview.background as ColorDrawable).color)
        val red: Int = tempColor shr 16 and 0xFF
        val green: Int = tempColor shr 8 and 0xFF
        val blue: Int = tempColor shr 0 and 0xFF
        return "$red $green $blue"
    }

    override fun finish() {
            val sendIntent = Intent().apply {
                    this.putExtra("colorText", this@MainActivity.getColorString())
                    this.type = "text/plain"
            }
        this.setResult(RESULT_OK, sendIntent)
            super.finish()
    }
}


