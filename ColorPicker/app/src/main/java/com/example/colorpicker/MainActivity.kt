package com.example.colorpicker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redNumberView.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if
            }
        })



        //redSeekBar.max = 255
        redSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onStopTrackingTouch(seekBar: SeekBar) { }
            override fun onStartTrackingTouch(seekBar: SeekBar) { }
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val colorStr = getColorString()
                redNumberView.setText(redSeekBar.progress)
                colorPreview.setBackgroundColor(Color.parseColor(colorStr))
            }
        })

        //greenSeekBar.max = 255
        greenSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onStopTrackingTouch(seekBar: SeekBar) { }
            override fun onStartTrackingTouch(seekBar: SeekBar) { }
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val colorStr = getColorString()
                greenNumberView.setText(greenSeekBar.progress)
                colorPreview.setBackgroundColor(Color.parseColor(colorStr))
            }
        })

        //blueSeekBar.max = 255
        blueSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onStopTrackingTouch(seekBar: SeekBar) { }
            override fun onStartTrackingTouch(seekBar: SeekBar) { }
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val colorStr = getColorString()
                blueNumberView.setText(greenSeekBar.progress)
                colorPreview.setBackgroundColor(Color.parseColor(colorStr))
            }
        })
    }

    fun getColorString(): String {
        var alpha = Integer.toHexString(255)
        var red = Integer.toHexString(((255*redSeekBar.progress)/redSeekBar.max))
        if (red.length == 1) red = "0" + red
        var green = Integer.toHexString(((255*greenSeekBar.progress)/greenSeekBar.max))
        if (green.length == 1) green = "0" + green
        var blue = Integer.toHexString(((255*blueSeekBar.progress)/blueSeekBar.max))
        if (blue.length == 1) blue = "0" + blue
        return "#" + alpha + red + green + blue
    }
}
