package edu.quintrix.assign21

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    val uidBox : EditText = findViewById<EditText>(R.id.uniqueID)
    val firstNameBox : EditText = findViewById<EditText>(R.id.uniqueID)
    val lastNameBox : EditText = findViewById<EditText>(R.id.uniqueID)
    val rewardsBox : EditText = findViewById<EditText>(R.id.uniqueID)

    val textInfo : TextView = findViewById<TextView>(R.id.text_info) as TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val db = DBHelper(this, null)

        }catch(e: Exception) {
            Log.e("DBFAIL","${e.cause} ${e.message}}")
        }

    }

    fun delete(view: View) {
        view.setOnClickListener {
            if ( uidBox.text.isNotEmpty() ) {
                val uid = uidBox.text.toString().toInt()

                //val rewards = rewards_box.text.toString().toDouble()
                val db = DBHelper(this, null)

                db.removeDataByUid(uid)

                Toast.makeText(this, "row removed @ uid of $uid", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "uid field cannot be empty!", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun displayInfo(view: View) {
        view.setOnClickListener {

            if (uidBox.text.isNotEmpty()) {
                val uid = uidBox.text.toString().toInt()
                val db = DBHelper(this,null)
                val result = db.getData(uid).toString()


                textInfo.setText(result)
                Toast.makeText(this, "row retrieved @ uid of $uid", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "uid field cannot be empty!", Toast.LENGTH_LONG).show()
            }


        }
    }
    fun add(view: View) {
        view.setOnClickListener {

            if ( uidBox.text.isNotEmpty() ) {
                val uid = uidBox.text.toString().toInt()
                val rewards :Double= (rewardsBox.text.toString().toDouble())
                val db = DBHelper(this, null)
                db.addData(uid, firstNameBox.text.toString(),lastNameBox.text.toString(),rewards)

                Toast.makeText(this, "new row added!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "uid field cannot be empty!", Toast.LENGTH_LONG).show()
            }



        }
    }


}