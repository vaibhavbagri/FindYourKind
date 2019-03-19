package com.example.vaibh.findyourkind


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class InfoActivity : AppCompatActivity() {
    lateinit var register : Button
    lateinit var mAuth: FirebaseAuth
    private lateinit var rootRef: DatabaseReference
    private lateinit var EventRef: DatabaseReference
    lateinit var mDatabase: DatabaseReference
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        rootRef = FirebaseDatabase.getInstance().reference
        EventRef = rootRef.child("Event")
        mAuth = FirebaseAuth.getInstance()
        mToolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(mToolbar)
        val name = findViewById<TextView>(R.id.eventName)
        val desc = findViewById<TextView>(R.id.eventDescription)
        val type = findViewById<TextView>(R.id.eventType)
        val loc = findViewById<TextView>(R.id.eventLocationTime)
        register = findViewById<Button>(R.id.eventRegister)
        name.setText(intent.getStringExtra("Name"))
        desc.setText(intent.getStringExtra("Description"))
        loc.setText(intent.getStringExtra("Location"))
        type.setText(intent.getStringExtra("Type"))
        val x = intent.getStringExtra("EventId")

        println("namsmdjbvjasbhDKsjLIdcbyuSHDciosjde")
        println(x)


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

        var reg = ""
        var flag = 0

        register.setOnClickListener{

            mDatabase.child("Attending").child(x).setValue("")
            EventRef.child(x).child("Registrations").child(cuser).setValue(username)
//                        .addOnCompleteListener( OnCompleteListener { task ->
//
//                if(task.isSuccessful){
//
//                    val intent = Intent(applicationContext, MainActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            })
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mToolbar.setNavigationOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        })
    }
}

