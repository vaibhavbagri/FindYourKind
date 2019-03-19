package com.example.vaibh.findyourkind

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.support.v7.widget.Toolbar
import com.example.vaibh.findyourkind.R.id.message
import com.example.vaibh.findyourkind.R.menu.navigation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_host_event.*

class HostEventActivity : AppCompatActivity() {

//    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId) {
//            R.id.navigation_home -> {
//                message.setText(R.string.title_home)
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_dashboard -> {
//                message.setText(R.string.title_dashboard)
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_notifications -> {
//                message.setText(R.string.title_notifications)
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }

    lateinit var mName: EditText
    lateinit var mType: EditText
    lateinit var mDate: EditText
    lateinit var mTime: EditText
    lateinit var mLocation: EditText
    lateinit var mMaxRegs: EditText
    lateinit var mDesc: EditText
    lateinit var mCreateButton: Button
    lateinit var mDatabase : DatabaseReference
    lateinit var mAuth : FirebaseAuth
    lateinit var mProgressBar : ProgressDialog
    private lateinit var rootRef: DatabaseReference
    private lateinit var EventRef: DatabaseReference
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_event)
        rootRef = FirebaseDatabase.getInstance().reference
        EventRef = rootRef.child("Event")
        mName = findViewById(R.id.eventName)
        mType = findViewById(R.id.eventType)
        mDate = findViewById(R.id.eventDate)
        mTime = findViewById(R.id.eventTime)
        mLocation = findViewById(R.id.eventLocation)
        mMaxRegs = findViewById(R.id.eventMaxRegs)
        mDesc = findViewById(R.id.eventDescription)
        mCreateButton = findViewById(R.id.eventCreate)
        mAuth = FirebaseAuth.getInstance()
        mProgressBar = ProgressDialog(this)
        mToolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(mToolbar)

        val uid = mAuth.currentUser!!.uid

        mCreateButton.setOnClickListener{

            mProgressBar.setMessage("Please wait")
            mProgressBar.show()

            mDatabase = FirebaseDatabase.getInstance().getReference("Event")
            val key = mDatabase.push().key.toString()
            var arrayList:ArrayList<String> = ArrayList()
            val name = mName.text.toString().trim()
            val type = mType.text.toString().trim()
            val date = mDate.text.toString().trim()
            val time = mTime.text.toString().trim()
            val location = mLocation.text.toString().trim()
            val mr = mMaxRegs.text.toString().trim()
            val maxregs = Integer.parseInt(mr)
            val desc = mDesc.text.toString().trim()



            val event = Event(uid,name,type,date,time,location,maxregs,desc,key)


            val cuser = mAuth.currentUser!!.uid

            var username = ""
            mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(cuser)
            mDatabase.child("name").addListenerForSingleValueEvent(object : ValueEventListener{

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {

                    username = p0.getValue(String::class.java).toString()
                }
            })
            mDatabase.child("Hosting").child(key).setValue("")

            mDatabase = FirebaseDatabase.getInstance().getReference("Event")



            mDatabase.child(key).setValue(event).addOnCompleteListener( OnCompleteListener { task ->

                if(task.isSuccessful){
                    EventRef.child(key).child("Registrations").child(cuser).setValue(username)
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                    mProgressBar.dismiss()
                }
            })


        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mToolbar.setNavigationOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        })

    }
}
