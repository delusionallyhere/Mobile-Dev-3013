package com.example.colorpicker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ColorViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.color_list_item, parent, false)) {

    private var colorNameView: TextView? = null
    private var colorValueView: TextView? = null
    private var colorBoxView: SurfaceView? = null

    init {
            colorNameView = itemView.findViewById(R.id.colorName)
            colorValueView = itemView.findViewById(R.id.colorValue)
            colorBoxView = itemView.findViewById(R.id.colorPreviewSquare)
    }

    fun bind(color: SavedColor) {
            colorNameView?.text = color.color_name
            val t = color.red_value.toString() + " " + color.green_value.toString() + " " + color.blue_value.toString()
            colorValueView?.text = t
            colorBoxView?.setBackgroundColor(Color.rgb(color.red_value, color.green_value, color.blue_value))
    }
}