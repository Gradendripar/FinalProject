package com.example.finalproject.ui.music

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.finalproject.R
import kotlinx.android.synthetic.main.fragment_music.*
import java.io.IOException
import kotlin.concurrent.thread

class MusicFragment : Fragment() {

    companion object {
        fun newInstance() = MusicFragment()
    }
    val mediaPlayer= MediaPlayer()
    lateinit var musicList:MutableList<String>
    lateinit var musicNameList:MutableList<String>

    private lateinit var musicViewModel: MusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        musicViewModel = ViewModelProviders.of(this).get(MusicViewModel::class.java)
        musicViewModel.musicLists.observe(viewLifecycleOwner, Observer {
            musicList=it
        })
        musicViewModel.musicNameLists.observe(viewLifecycleOwner, Observer {
            musicNameList=it
        })
        mediaPlayer.setOnPreparedListener {
            it.start()
        }
        mediaPlayer.setOnCompletionListener {
            musicViewModel.setOnCompletionListener()
//            play()
            musicViewModel.notification()

        }
        if(context?.let { ContextCompat.checkSelfPermission(it,android.Manifest.permission.READ_EXTERNAL_STORAGE) } != PackageManager.PERMISSION_GRANTED){
            activity?.let { ActivityCompat.requestPermissions(it, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),0) }
        }else{
            musicViewModel.getMusicList()
        }
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress:Int,
                fromUser:Boolean
            ) {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        thread {
            while (true){
                Thread.sleep(1000)
                activity?.runOnUiThread {
                    seekBar.max=mediaPlayer.duration
                    seekBar.progress=mediaPlayer.currentPosition
                }
            }
        }
        play.setOnClickListener {
            play()
        }
        pause.setOnClickListener {
            if(!musicViewModel.isPause){
                mediaPlayer.start()
            }else{
                mediaPlayer.pause()
                musicViewModel.isPause = true
            }
        }
        stop.setOnClickListener {
            mediaPlayer.stop()
        }
        next.setOnClickListener {
            musicViewModel.onNext()
            musicViewModel.notification()
            play()
        }
        prev.setOnClickListener {
            musicViewModel.onPrev()
            play()
        }
    }
    fun play(){
        val current=musicViewModel.current
        if(musicList.size==0) return
        val path=musicList[current]
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepareAsync()
            textView_menu.text=musicNameList[current]
            textView_count.text="${current+1}/${musicList.size}"
        }catch (e: IOException){
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        musicViewModel.getMusicList()
    }

}