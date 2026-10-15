package com.example.musicplayer

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import androidx.constraintlayout.widget.ConstraintsChangedListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val mp = MediaPlayer.create(this, R.raw.music)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnFront = findViewById<Button>(R.id.btnFront)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnPlay.setOnClickListener({
            if (!mp.isPlaying)
                mp.start()
            else
                mp.pause()
        })

        btnFront.setOnClickListener(
            {
                mp.seekTo(mp.currentPosition + 10000)
            }
        )

        btnBack.setOnClickListener({
            mp.seekTo(mp.currentPosition - 10000)
        })

        val seekProgress: SeekBar = findViewById<SeekBar>(R.id.progress)
        seekProgress.max = mp.duration
        seekProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2)
                    mp.seekTo(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                seekProgress.progress = mp.currentPosition
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)

        val seekVolume: SeekBar = findViewById<SeekBar>(R.id.volume)

        val audioManager: AudioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        val maxVolume: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVolume: Int = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        seekVolume.max = maxVolume

        seekVolume.progress = currentVolume

        seekVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, p1, 0)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }
}