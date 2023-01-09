package org.example;

import java.io.PrintWriter;

public class Persoana extends Utilizator implements InlocuireBuletin, InlocuireCarnetDeSofer {

    public Persoana(String nume) {
        super(nume);
    }

    @Override
    public void printCerereUtilizator(PrintWriter pw, Cerere cerere) {
        pw.print(cerere.getData() + " - Subsemnatul " + cerere.getUtilizator().nume);
        pw.println(", va rog sa-mi aprobati urmatoarea solicitare: " + cerere.convertTypeToString());
    }
}
