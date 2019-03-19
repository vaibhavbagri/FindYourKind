package com.example.vaibh.findyourkind

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity() : AppCompatActivity(), Parcelable {


    lateinit var listView: ListView
    lateinit var mToolbar : Toolbar
    lateinit var reference : DatabaseReference
    lateinit var reference2 : DatabaseReference
    lateinit var eventList: MutableList<Event>
    lateinit var mAuth:FirebaseAuth
    lateinit var sp : SharedPreferences
    private lateinit var rootRef: DatabaseReference
    private lateinit var EventRef: DatabaseReference

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        eventList= mutableListOf()
        reference = FirebaseDatabase.getInstance().getReference("Event")
        reference2 = FirebaseDatabase.getInstance().getReference("Users")
        sp = getSharedPreferences("login", Context.MODE_PRIVATE)
        listView=findViewById(R.id.listView)
        mToolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.title = "Find Your Kind"
        mToolbar.setTitleTextColor(Color.WHITE)
        var count=0;
        var arrayList:ArrayList<String> = ArrayList()
        var myList:ArrayList<String> = ArrayList()
        arrayList.add("Food")//Adding object in arraylist
        arrayList.add("Sci Fi")
        arrayList.add("Tech")
        arrayList.add("Literary")
        arrayList.add("Motivation")

        mAuth=FirebaseAuth.getInstance()
        val UID=mAuth.currentUser!!.uid
        var interests : ArrayList<String> = ArrayList()
        var eventList : ArrayList<Event> = ArrayList()
        var reg = ""


        rootRef = FirebaseDatabase.getInstance().reference
        EventRef = rootRef.child("Event")
        var t=1
        reference2=FirebaseDatabase.getInstance().getReference("Users").child(UID).child("Interest")
        reference2.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                println("aehgndwicvasyuidujbjdknhba   ENTEREDDDDDDD")

                count++
                val `val` = dataSnapshot.value!!.toString()
                interests.add(`val`)
                println(interests)
                if(count==5)
                {
                    reference.addValueEventListener(object: ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(p0: DataSnapshot) {
//                            eventList.clear()
                            t=0


                            println("aehgndwicvasyuidujbjdknhba   ENTEREDDDDDDD")
                            for(i in interests.indices)
                            {
                                //Toast.makeText(this, "Enter for loop", Toast.LENGTH_LONG).show()

                                if(interests[i].compareTo('1'.toString())==0)
                                {
                                    // Toast.makeText(this, "Enter if", Toast.LENGTH_LONG).show()

                                    myList.add(arrayList.get(i))
                                }

                            }
                            // if(p0!!.exists()){
                            for(c in p0.children)
                            {
                                val event=c.getValue(Event::class.java)!!

                                for(i in myList)
                                {
                                    var flag=0
                                    if(i.compareTo(event.type)==0)
                                    {


                                        EventRef.child(event.eventId).child("Registrations").
                                                addListenerForSingleValueEvent(object: ValueEventListener{
                                                    override fun onCancelled(p0: DatabaseError) {
                                                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                                    }


                                                    override fun onDataChange(p0: DataSnapshot) {
println("Random dakjvoidvhbaudaodhcaoichaoihaodihcoa")

                                                        for(ds: DataSnapshot in p0.children){

                                                            reg = ds.key!!
                                                            if ( UID.compareTo(reg!!) == 0){

                                                                flag = 1
                                                                break
                                                            }
                                                        }

                                                        if ( flag != 1 && !eventList.contains(event) )
                                                        {

                                                            eventList.add(event)
                                                        }

                                                    }
                                                })
                                    }

                                }
                            }
                            println("adckadhvcahdvcaidc ehehekkkk please print ")
                            val adapter=EventAdapter(applicationContext,R.layout.info,eventList)
                            listView.adapter=adapter

                        }
                    })

                }

                listView.setOnItemClickListener { parent, view, position, id->

                    val intent = Intent(this@MainActivity, InfoActivity::class.java)
                    intent.putExtra("Name",eventList[position].name)
                    intent.putExtra("Description",eventList[position].desc)
                    intent.putExtra("Type",eventList[position].type)
                    intent.putExtra("Location",eventList[position].location)
                    intent.putExtra("EventId",eventList[position].eventId)


                    startActivity(intent)}

            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        if(t==1)
        {

            val adapter=EventAdapter(applicationContext,R.layout.info,eventList)
            listView.adapter=adapter
        }
        t=1









//        reference.addValueEventListener(object: ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                for(i in interests.indices)
//                {
//                    //Toast.makeText(this, "Enter for loop", Toast.LENGTH_LONG).show()
//                    println("ENTEREERERE")
//                    if(interests.get(i).equals('1'))
//                    {
//                       // Toast.makeText(this, "Enter if", Toast.LENGTH_LONG).show()
//                        println("Enteradiojvfviodsj'fovjersyfiwefonae;vjaleszx SHIVANIIIII")
//                        myList.add(arrayList.get(i))
//                    }
//
//                }
//                if(p0!!.exists()){
//                    for(c in p0.children)
//                    {
//                        val event=c.getValue(Event::class.java)!!
//
//                       for(i in myList)
//                       {
//                           if(i.equals(event.type))
//                           {
//                               eventList.add(event)
//                                println("DfbyjZDHvfbi;gdlfsiusfhvjkdzbfvkjsnc BLAHHHHH")
//                           break
//                           }
//
//                       }
//                    }
//                    val adapter=EventAdapter(applicationContext,R.layout.info,eventList)
//                    listView.adapter=adapter
//
//                }
//            }
//        })

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.menuLogout){

            FirebaseAuth.getInstance().signOut()
            sp.edit().putBoolean("logged", false).apply()
            val startIntent = Intent (applicationContext, LoginActivity::class.java)
            startActivity(startIntent)
            finish()

        }

        if(item?.itemId == R.id.menuProfile){

//            Toast.makeText(applicationContext, "Profile", Toast.LENGTH_LONG).show()

            val startIntent = Intent (applicationContext, UserProfileActivity::class.java)
            startActivity(startIntent)
            finish()
        }

        if(item?.itemId == R.id.menuHostEvent){

//            Toast.makeText(applicationContext, "Profile", Toast.LENGTH_LONG).show()

            val startIntent = Intent (applicationContext, HostEventActivity::class.java)
            startActivity(startIntent)
            finish()
        }

        return true

    }
}

