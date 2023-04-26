package com.hemant.stickynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hemant.stickynotes.models.NotesModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    final static String DBNAME = "noteDB";
    final static int DBVERSION = 1;

    public DBHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table notes" +
                "(id integer primary key autoincrement," +
                "title text," +
                "description text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("drop table if exists notes");
        onCreate(db);
    }

    public boolean insertNote(String title, String desc){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("description",desc);
        long id = db.insert("notes",null,values);
        return id != -1;
    }

    public Cursor getNotes(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from notes",null);
        return cursor;
    }

    public boolean updateNote(int id,String title, String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("description",desc);

        int isUpdated = db.update("notes",values,"id ="+id,null);
        return isUpdated!=-1;
    }

    public boolean deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        float isDeleted = db.delete("notes","id= "+id,null);
        return isDeleted!=-1;
    }
}
