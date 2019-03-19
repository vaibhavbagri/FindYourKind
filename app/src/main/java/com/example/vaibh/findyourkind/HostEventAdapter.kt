package com.example.vaibh.findyourkind

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class MyPostAdapter(val contextt: Context, val layoutResId: Int, val eventList: List<Event>)
    : ArrayAdapter<Event>(contextt, layoutResId, eventList) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val mInflater: LayoutInflater = LayoutInflater.from(contextt)
        val view: View = mInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        val textViewLocationTime = view.findViewById<TextView>(R.id.textViewLocationTime)
        val textViewType = view.findViewById<TextView>(R.id.textViewType)
//        val eventUpdateButton = view.findViewById<Button>(R.id.postUpdateButton)
        val eventDeleteButton = view.findViewById<Button>(R.id.postDeleteButton)

        val event = eventList[position]

        textViewName.text = event.name
        val loctime = event.location+""+event.time
        textViewLocationTime.text = loctime
        textViewType.text = event.type

//        postUpdateButton.setOnClickListener{
//
//            updatePost(post)
//
//        }
//
        eventDeleteButton.setOnClickListener{
            deleteEvent(event)
        }

        return view
    }

    //    private fun updatePost(post: Posts) {
//
//        val intent = Intent(contextt, UpdatePostActivity::class.java)
//        intent.putExtra("title", post.title)
//        intent.putExtra("body", post.body)
//        intent.putExtra("postId", post.postId)
//        contextt.startActivity(intent)
//
//    }
//
//
    private fun deleteEvent(event: Event) {
        val mDatabase = FirebaseDatabase.getInstance().getReference("Event")
        mDatabase.child(event.eventId).removeValue()
        Toast.makeText(contextt,"Deleted !",Toast.LENGTH_LONG).show()
    }
}