package com.example.vaibh.findyourkind

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList


class SelectInterestActivity : AppCompatActivity() {

    private var saveButton: Button? = null
    private lateinit var rootRef: DatabaseReference
    internal lateinit var userRef: DatabaseReference
    private var mFirstCheckBox: CheckBox? = null
    private var mSecondCheckBox: CheckBox? = null
    private var mThirdCheckBox: CheckBox? = null
    private var mFourthCheckBox: CheckBox? = null
    private var mFifthCheckBox: CheckBox? = null
    private var mTextView: TextView? = null
    private val checkbox = ArrayList<String>()
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_interest)


        rootRef = FirebaseDatabase.getInstance().reference
        userRef = rootRef.child("Users")
        mAuth = FirebaseAuth.getInstance()
        saveButton = findViewById(R.id.button)
        mTextView = findViewById(R.id.textView)
        mFirstCheckBox = findViewById(R.id.checkBox5)
        mSecondCheckBox = findViewById(R.id.checkBox6)
        mThirdCheckBox = findViewById(R.id.checkBox7)
        mFourthCheckBox = findViewById(R.id.checkBox8)
        mFifthCheckBox = findViewById(R.id.checkBox9)


        saveButton!!.setOnClickListener(View.OnClickListener {


            if (mFirstCheckBox!!.isChecked()) {
                checkbox.add("1")
            }
            else
                checkbox.add("0")

            if (mSecondCheckBox!!.isChecked()) {
                checkbox.add("1")
            }
            else
                checkbox.add("0")

            if (mThirdCheckBox!!.isChecked()) {
                checkbox.add("1")
            }
            else
                checkbox.add("0")

            if (mFourthCheckBox!!.isChecked()) {
                checkbox.add("1")
            }
            else
                checkbox.add("0")

            if (mFifthCheckBox!!.isChecked()) {
                checkbox.add("1")
            }
            else
                checkbox.add("0")

            val cuser = mAuth.currentUser!!.uid
            userRef.child(cuser).child("Interest").setValue(checkbox)

            val interestIntent = Intent(this@SelectInterestActivity,MainActivity::class.java)
            interestIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(interestIntent)

        })
    }
}

