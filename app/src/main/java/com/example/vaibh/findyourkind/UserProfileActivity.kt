package com.example.vaibh.findyourkind

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserProfileActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    lateinit var eventHostList: MutableList<Event>
    lateinit var eventAttendList: MutableList<Event>
    lateinit var HostListView: ListView
    lateinit var AttendListView: ListView
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference("Event")
        val uid = mAuth.currentUser!!.uid
        eventHostList = mutableListOf()
        eventAttendList = mutableListOf()
        HostListView = findViewById(R.id.hostingListView)
//        AttendListView = findViewById(R.id.attendingListView)
        mToolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(mToolbar)
        mDatabase.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    eventHostList.clear()
                    for (h in dataSnapshot.children) {
                        val event = h.getValue(Event::class.java)
                        if (event!!.host == uid)
                            eventHostList.add(event)
                    }

                    val adapter = MyPostAdapter(applicationContext, R.layout.row_item_hosting, eventHostList)
                    HostListView.adapter = adapter

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }

        })

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mToolbar.setNavigationOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        })


//        val context = this
//        mListView.setOnItemClickListener { _, _, position, _ ->
//            val selectedPost = postList[position]
//            val detailIntent = IndividualPostActivity.newIntent(context, selectedPost)
//            startActivity(detailIntent)
//        }


    }
}
