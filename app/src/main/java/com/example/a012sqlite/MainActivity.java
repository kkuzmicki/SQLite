package com.example.a012sqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StudentRecyclerViewAdapter.ItemClickListener
{
    TextView textView;
    StudentRecyclerViewAdapter adapter;
    StudentsDBHelper studentsDBHelper;
    List<Student> studentList;
    RecyclerView recyclerView;

    String sort = "nazwisko";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        studentsDBHelper = new StudentsDBHelper(this);
        // Uwaga! dodanie tych pozycji możliwe jest tylko wtedy gdy nie istnieją jeszcze wpisy w bazie z takimi nr albumu
        studentsDBHelper.addStudentToDB(new Student(12223,"Andrzej","Nowak", 1999, "informatyka", 3.5f));
        studentsDBHelper.addStudentToDB(new Student(23523,"Tomasz","Dudek", 1998, "mechatronika", 4.0f));
        studentsDBHelper.addStudentToDB(new Student(98123,"Adam","Małysz", 1997, "elektrotechnika", 3.0f));
        studentList = studentsDBHelper.getAllStudents("nazwisko");
        recyclerView = findViewById(R.id.studentRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentRecyclerViewAdapter(this, studentList); // ustawinie listenera dla adaptera
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        System.out.println("test");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.settings_item)
        {
            // utworzenie nowej intecji
            Intent intent = new Intent(this, DodawanieActivity.class);
            intent.putExtra("lista", (ArrayList<Student>) studentList);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                studentList = studentsDBHelper.getAllStudents("nazwisko");
                adapter = new StudentRecyclerViewAdapter(this, studentList);
                adapter.setClickListener(this);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    // implementacja metody interfejsu adaptera ItemClickListener
    @Override
    public void onDeleteItemClick(View view, final int position, final Student student)
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Potwierdzenie usunięcia elementu o indeksie [" + position + "].");
        alert.setTitle("Potwierdzenie");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                studentsDBHelper.deleteStudent(student);
                studentList.remove(student);
                //metoda odświeżająca widok po odjęciu jednej z pozycji
                adapter.notifyItemRemoved(position);
            }
        });

        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                // nic
            }
        });
        alert.show();
    }

    @Override
    public void onTextClick(View view, int position, final Student student)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Informacje szczegółowe o studencie");
        alert.setMessage("Numer indeksu: " + student.getNrAlbumu() + " | Imię: " + student.getImie() + " | Nazwisko: " + student.getNazwisko() +
                " | Rok urodzenia: " + student.getUrodziny() + " | Kierunek: " + student.getKierunek() + " | Średnia: " + student.getSrednia());
        alert.show();
    }

    @Override
    public void onEditItemClick(View view, int position, final Student student)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Edycja elementu o indeksie [" + position + "]");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);


        final EditText numerET = new EditText(getApplicationContext());
        numerET.setText(String.valueOf(student.getNrAlbumu()));
        numerET.setHint("Numer indeksu");
        layout.addView(numerET);

        final EditText imieET = new EditText(getApplicationContext());
        imieET.setText(student.getImie());
        imieET.setHint("Imię");
        layout.addView(imieET);

        final EditText nazwiskoET = new EditText(getApplicationContext());
        nazwiskoET.setText(student.getNazwisko());
        nazwiskoET.setHint("Nazwisko");
        layout.addView(nazwiskoET);

        final EditText urodzinyET = new EditText(getApplicationContext());
        urodzinyET.setText(String.valueOf(student.getUrodziny()));
        urodzinyET.setHint("Rok urodzenia");
        layout.addView(urodzinyET);

        final EditText kierunekET = new EditText(getApplicationContext());
        kierunekET.setText(student.getKierunek());
        kierunekET.setHint("Kierunek");
        layout.addView(kierunekET);

        final EditText sredniaET = new EditText(getApplicationContext());
        sredniaET.setText(String.valueOf(student.getSrednia()));
        sredniaET.setHint("Średnia");
        layout.addView(sredniaET);

        alert.setView(layout);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                int numer;
                try
                {
                    numer = Integer.parseInt(numerET.getText().toString());
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Źle wprowadzony numer albumu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String imie = imieET.getText().toString();
                if(imie.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Źle wprowadzone imię!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String nazwisko = nazwiskoET.getText().toString();
                if(nazwisko.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Źle wprowadzone nazwisko!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int urodziny;
                try
                {
                    urodziny = Integer.parseInt(urodzinyET.getText().toString());
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Źle wprowadzony rok urodzenia!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String kierunek = kierunekET.getText().toString();
                if(kierunek.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Źle wprowadzony kierunek!", Toast.LENGTH_SHORT).show();
                    return;
                }

                float srednia;
                try
                {
                    srednia = Float.parseFloat(sredniaET.getText().toString());
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Źle wprowadzona średnia!", Toast.LENGTH_SHORT).show();
                    return;
                }

                studentsDBHelper.deleteStudent(student);
                studentList.remove(student);
                studentsDBHelper.addStudentToDB(new Student(numer, imie, nazwisko, urodziny, kierunek, srednia));
                sortuj();
            }
        });

        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                // nic
            }
        });

        alert.show();
    }

    private void sortuj()
    {
        studentList = studentsDBHelper.getAllStudents(sort);
        adapter = new StudentRecyclerViewAdapter(this, studentList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void sortujNumer(View view)
    {
        sort = "nr_albumu";
        sortuj();
    }

    public void sortujImie(View view)
    {
        sort = "imie";
        sortuj();
    }

    public void sortujNazwisko(View view)
    {
        sort = "nazwisko";
        sortuj();
    }

    public void sortujUrodziny(View view)
    {
        sort = "urodziny";
        sortuj();
    }

    public void sortujKierunek(View view)
    {
        sort = "kierunek";
        sortuj();
    }

    public void sortujSrednia(View view)
    {
        sort = "srednia";
        sortuj();
    }
}