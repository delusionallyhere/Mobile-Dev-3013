package com.example.flashbeepshake

import android.content.Context
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flashButton = findViewById<Button>(R.id.flashButton)
        val beepButton = findViewById<Button>(R.id.beepButton)
        val shakeButton = findViewById<Button>(R.id.shakeButton)
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraID = cameraManager.cameraIdList[0]
        val tone = ToneGenerator(AudioManager.STREAM_MUSIC, 100)

        var flashOn = false

        flashButton.setOnClickListener {
            if(!flashOn) {
                flashOn = true
                cameraManager.setTorchMode(cameraID, true)
            }
            else {
                flashOn = false
                cameraManager.setTorchMode(cameraID, false)
            }
        }

        beepButton.setOnClickListener {
            tone.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 500)
        }

        shakeButton.setOnClickListener {
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                vibrator.vibrate(300)
            }
        }
    }
}
