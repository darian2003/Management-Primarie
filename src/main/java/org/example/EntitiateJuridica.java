package org.example;

import java.io.PrintWriter;

public class EntitiateJuridica extends Utilizator implements ReinnoireAutorizatie, CreareActConstitutiv{

    String reprezentant;

    public EntitiateJuridica(String nume, String reprezentant) {
        super(nume);
        this.reprezentant = reprezentant;
    }

    @Override
    public void printCerereUtilizator(PrintWriter pw, Cerere cerere) {
        pw.print(cerere.getData() + " - Subsemnatul " + this.reprezentant);
        pw.println(", reprezentant legal al companiei " + this.nume + ", va rog sa-mi aprobati urmatoarea solicitare: " + cerere.convertTypeToString());
    }
}
