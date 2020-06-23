package com.example.a012sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;

public class StudentsDBHelper extends SQLiteOpenHelper
{
    public static String DB_NAME = "students.db";
    public StudentsDBHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        System.out.println("onCreate");
        db.execSQL("create table studenci(nr_albumu integer primary key, imie text, nazwisko text, urodziny integer, kierunek text, srednia real);");
        System.out.println("onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        System.out.println("onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS studenci");
        onCreate(db);
        System.out.println("onUpgrade");
    }

    public boolean addStudentToDB(Student student)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nr_albumu", student.getNrAlbumu());
        values.put("imie", student.getImie());
        values.put("nazwisko", student.getNazwisko());
        values.put("urodziny", student.getUrodziny());
        values.put("kierunek", student.getKierunek());
        values.put("srednia", student.getSrednia());
        try
        {
            db.insertOrThrow("studenci",null, values);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Student> getAllStudents(String sort)
    {
        List<Student> listStudentow = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query("studenci",null,null,null,null,null, sort);
        //cursor.moveToFirst();
        while (cursor.moveToNext())
        {
            Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getFloat(5));
            listStudentow.add(student);
        }
        return listStudentow;
    }

    public void deleteStudent(Student student)
    {
        SQLiteDatabase db = getWritableDatabase();
        String arg[] = new String[]{String.valueOf(student.getNrAlbumu())};
        db.delete("studenci","nr_albumu=?", arg);
    }

    public void eraseDataBase()
    {

    }
}