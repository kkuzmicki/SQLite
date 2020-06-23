package com.example.a012sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DodawanieActivity extends AppCompatActivity
{
    private ArrayList<Student> studenci;

    EditText numerET;
    EditText imieET;
    EditText nazwiskoET;
    EditText urodzinyET;
    EditText kierunekET;
    EditText sredniaET;

    StudentsDBHelper studentsDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie);
        init();
        studentsDBHelper = new StudentsDBHelper(this);
    }

    private void init()
    {
        numerET = findViewById(R.id.numerET);
        imieET = findViewById(R.id.imieET);
        nazwiskoET = findViewById(R.id.nazwiskoET);
        urodzinyET = findViewById(R.id.urodzinyET);
        kierunekET = findViewById(R.id.kierunekET);
        sredniaET = findViewById(R.id.sredniaET);
    }

    public void dodaj(View view)
    {
        if(numerET.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Wprowadź numer albumu!", Toast.LENGTH_SHORT).show();
            return;
        }
        int nrAlbumu = Integer.parseInt(numerET.getText().toString());

        if(imieET.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Wprowadź imię!", Toast.LENGTH_SHORT).show();
            return;
        }
        String imie = imieET.getText().toString();

        if(nazwiskoET.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Wprowadź nazwisko!", Toast.LENGTH_SHORT).show();
            return;
        }
        String nazwisko = nazwiskoET.getText().toString();

        if(urodzinyET.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Wprowadź rok urodzenia!", Toast.LENGTH_SHORT).show();
            return;
        }
        int urodziny = Integer.parseInt(urodzinyET.getText().toString());

        if(kierunekET.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Wprowadź kierunek!", Toast.LENGTH_SHORT).show();
            return;
        }
        String kierunek = kierunekET.getText().toString();

        if(sredniaET.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Wprowadź średnią!", Toast.LENGTH_SHORT).show();
            return;
        }
        float srednia = Integer.parseInt(sredniaET.getText().toString());

        studentsDBHelper.addStudentToDB(new Student(nrAlbumu, imie, nazwisko, urodziny, kierunek, srednia));

        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
