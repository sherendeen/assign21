package edu.quintrix.assign21

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DBHelper (var context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {


        companion object {
            private val DATABASE_NAME = "android_class"
            private val DATABASE_VERSION = 1
            val table_name = "rewards_users"
            val id_col = "id"
            val firstname_col = "firstname"
            val lastname_col = "lastname"
            val rewards_col = "rewards"
        }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + table_name + " ("+ id_col+" INTEGER PRIMARY KEY, " +
                firstname_col + " TEXT," + lastname_col+ " TEXT," + rewards_col + " Number)")
        Log.i("TABLE","creating table $query")
        try {
            db.execSQL(query)
        } catch (e : Exception) {
            e.printStackTrace()
            Log.e("DBFAIL","${e.cause} ${e.message}}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS "+ table_name)
        onCreate(db)
    }

    fun addData(uid:Int, firstname:String, lastname:String, rewards:Double) {
        val values = ContentValues()
        values.put(id_col, uid)
        values.put(firstname_col,firstname)
        values.put(lastname_col, lastname)
        values.put(rewards_col, rewards)
        val db = this.writableDatabase

        db.insert(table_name,null, values)

        db.close()
    }

    fun getData():Cursor?{
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        Log.e("DB CURSOR","Selecting data from table now")
        return db.rawQuery("SELECT * FROM " + table_name, null)

    }

    fun getData(uid:Int):String{
        val db = this.readableDatabase
        var result = "No data in DB"
        val cursor : Cursor  =
            db.rawQuery("SELECT * FROM " + table_name + " WHERE $id_col='"+uid+"';" ,
                null)
        try{
            if(cursor.count != 0){
                val stringBuffer = StringBuffer()
                while( cursor.moveToNext()){
                    stringBuffer.append("ID :" + cursor.getInt(0).toString() + "\n")
                    stringBuffer.append("FIRST NAME :" + cursor.getString(1).toString() + "\n")
                    stringBuffer.append("LAST NAME :" + cursor.getString(2).toString() + "\n")
                    stringBuffer.append("REWARD :" + cursor.getDouble(3).toString() + "\n\n")
                }
                result = stringBuffer.toString()
            }
        } catch (e:Exception) {
            e.printStackTrace()
        }
        return result

    }

    fun removeDataByUid(uid:Int):Cursor?{
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        Log.e("DB CURSOR","Selecting data from table where uid = $uid now")
        return db.rawQuery("DELETE * FROM " + table_name + " WHERE uid='"+uid+"';" , null)
    }
}