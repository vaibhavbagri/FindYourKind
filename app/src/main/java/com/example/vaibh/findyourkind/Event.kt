package com.example.vaibh.findyourkind

import java.util.*
import kotlin.collections.ArrayList

class Event (val host: String, val name: String, val type: String, val date: String, val time: String, val location: String, val max: Int, val desc: String, val eventId: String) {

    constructor() :  this("","","","","","",0,"","")

}