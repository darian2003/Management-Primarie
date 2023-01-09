package org.example;

import java.io.PrintWriter;

public class Elev extends Persoana implements InlocuireCarnetDeElev{
    String scoala;

    public Elev(String nume, String scoala) {
        super(nume);
        this.scoala = scoala;
    }


    @Override
    public void printCerereUtilizator(PrintWriter pw, Cerere cerere) {
        pw.print(cerere.getData() + " - Subsemnatul " + cerere.getUtilizator().nume);
        pw.println(", elev la scoala " + this.scoala + ", va rog sa-mi aprobati urmatoarea solicitare: " + cerere.convertTypeToString());
    }
}
