package edu.quintrix.assign21

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uidBox = findViewById<EditText>(R.id.uniqueID)
        val firstNameBox = findViewById<EditText>(R.id.uniqueID)
        val lastNameBox = findViewById<EditText>(R.id.uniqueID)
        val rewardsBox = findViewById<EditText>(R.id.uniqueID)

        val textInfo: TextView = findViewById<TextView>(R.id.text_info) as TextView

        val btn_show = findViewById<Button>(R.id.button_display_info)
        val btn_add = findViewById<Button>(R.id.button_add)
        val btn_remove =findViewById<Button>(R.id.button_delete)

        try {
            val db = DBHelper(this, null)

        } catch (e: Exception) {
            Log.e("DBFAIL", "${e.cause} ${e.message}}")
        }

        btn_show.setOnClickListener {
            displayInfo(uidBox, textInfo)
        }

        btn_add.setOnClickListener {
            add(uidBox, firstNameBox, lastNameBox, rewardsBox)
        }

        btn_remove.setOnClickListener {

            delete(uidBox)
        }

    }

    fun delete(uidBox : EditText) {
        if (uidBox.text.isNotEmpty()) {
            val uid = uidBox.text.toString().toInt()

            val db = DBHelper(this, null)

            db.removeDataByUid(uid)

            Toast.makeText(this, "row removed @ uid of $uid", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "uid field cannot be empty!", Toast.LENGTH_LONG).show()
        }
    }

    fun displayInfo(uidBox: EditText, textInfo : TextView) {

        if (uidBox.text.isNotEmpty()) {
            val uid = uidBox.text.toString().toInt()
            val db = DBHelper(this, null)
            val result = db.getData(uid).toString()


            textInfo.text = result
            Toast.makeText(this, "row retrieved @ uid of $uid", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "uid field cannot be empty!", Toast.LENGTH_LONG).show()
        }


    }

    fun add(uidBox: EditText, firstNameBox:EditText, lastNameBox:EditText, rewardsBox:EditText) {


        if (uidBox.text.isNotEmpty()) {
            val uid = uidBox.text.toString().toInt()
            val rewards: Double = (rewardsBox.text.toString().toDouble())
            val db = DBHelper(this, null)
            db.addData(uid, firstNameBox.text.toString(), lastNameBox.text.toString(), rewards)

            Toast.makeText(this, "new row added!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "uid field cannot be empty!", Toast.LENGTH_LONG).show()
        }
    }


}