package com.example.vaibh.findyourkind

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class EventAdapter(val mCtx: Context, val layoutResId: Int, val eventList: List<Event>)
    : ArrayAdapter<Event>(mCtx,layoutResId,eventList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx);
        val view: View = layoutInflater.inflate(layoutResId, null)
        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        val textViewLocationTime = view.findViewById<TextView>(R.id.textViewLocationTime)
        val textViewType = view.findViewById<TextView>(R.id.textViewType)
        val eventob = eventList[position]

        textViewName.text = eventob.name
        val loctime = eventob.location+""+eventob.time
        textViewLocationTime.text = loctime
        textViewType.text = eventob.type

        return view
    }
}


