package com.example.a012sqlite;

import java.io.Serializable;

class Student implements Serializable
{
    int nrAlbumu;
    String imie;
    String nazwisko;
    int urodziny;
    String kierunek;
    float srednia;

    public Student(int nrAlbumu, String imie, String nazwisko, int urodziny, String kierunek, float srednia)
    {
        this.nrAlbumu = nrAlbumu;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.urodziny = urodziny;
        this.kierunek = kierunek;
        this.srednia = srednia;
    }

    public int getNrAlbumu()
    {
        return nrAlbumu;
    }

    public void setNrAlbumu(int nrAlbumu)
    {
        this.nrAlbumu = nrAlbumu;
    }

    public String getImie()
    {
        return imie;
    }

    public void setImie(String imie)
    {
        this.imie = imie;
    }
    public String getNazwisko()
    {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko)
    {
        this.nazwisko = nazwisko;
    }

    public int getUrodziny()
    {
        return urodziny;
    }

    public void setUrodziny(int urodziny)
    {
        this.urodziny = urodziny;
    }

    public String getKierunek()
    {
        return kierunek;
    }

    public void setKierunek(String kierunek)
    {
        this.kierunek = kierunek;
    }

    public float getSrednia()
    {
        return srednia;
    }

    public void setSrednia(float srednia)
    {
        this.srednia = srednia;
    }

    @Override
    public String toString()
    {
        return nrAlbumu+": "+imie+" "+nazwisko;
    }
}