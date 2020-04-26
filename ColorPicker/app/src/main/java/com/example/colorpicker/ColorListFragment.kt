package com.example.colorpicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.color_list_fragment.*
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream


data class SavedColor(val color_name: String, val red_value: Int, val green_value: Int, val blue_value: Int)

class ColorListFragment : Fragment() {
    private val savedColorList = ArrayList<SavedColor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getSavedColors()
        return inflater.inflate(R.layout.color_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        color_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ListAdapter(savedColorList)
        }
    }

    fun getSavedColors(): ArrayList<SavedColor>{

        try {
            val file = File(context!!.filesDir.absolutePath + "/colors.txt")
            val inputStream: InputStream = file.inputStream()
            val colorList = mutableListOf<String>()
            if(file.exists()) {
                inputStream.bufferedReader()
                    .useLines { colors -> colors.forEach { colorList.add(it) } }
                colorList.forEach {
                    println(it)
                    if (!colorArray.contains(it)) {
                        colorArray.add(it)
                    }
                    colorArray.forEach { color ->
                        colorArray
                        val tempColor = color.split(" ")
                        println(tempColor)
                        val tempName = tempColor[0]
                        val tempRval = tempColor[1].toInt()
                        val tempGval = tempColor[2].toInt()
                        val tempBval = tempColor[3].toInt()
                        if(!savedColorList.contains(SavedColor(tempName, tempRval, tempGval, tempBval))){
                            savedColorList.add(SavedColor(tempName, tempRval, tempGval, tempBval))
                        }
                    }
                }
            }

        }catch (e: FileNotFoundException){

        }
        return savedColorList
    }

    fun updateColors(){
        color_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ListAdapter(savedColorList)
        }
    }

    companion object{
        fun newInstance(): ColorListFragment = ColorListFragment()
    }
}