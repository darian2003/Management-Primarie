package org.example;

import java.io.PrintWriter;

public class Angajat extends Persoana implements InregistrareVenitSalarial, InlocuireCarnetDeSofer{
    private String numeCompanie;

    public Angajat(String nume, String numeCompanie) {
        super(nume);
        this.numeCompanie = numeCompanie;
    }

    public String getNumeCompanie() {
        return numeCompanie;
    }

    @ Override
    public void printCerereUtilizator(PrintWriter pw, Cerere cerere) {
        pw.print(cerere.getData() + " - Subsemnatul " + cerere.getUtilizator().nume);
        pw.println(", angajat la compania " + this.getNumeCompanie() + ", va rog sa-mi aprobati urmatoarea solicitare: " + cerere.convertTypeToString());
    }
}
