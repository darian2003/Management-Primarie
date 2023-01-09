package org.example;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Birou {

    protected PriorityQueue<Cerere> cereriBirou = new PriorityQueue<>(50, new ComparatorCerere());
    protected ArrayList<FunctionarPublic> functionari = new ArrayList<>();

    public void afiseazaCoadaCereriBirou(PrintWriter printWriter) {
        ArrayList<Cerere> tempArray = new ArrayList<>();
        printWriter.println(" - cereri in birou:");
        while (!this.cereriBirou.isEmpty()) {
            Cerere cerere = this.cereriBirou.poll();
            printWriter.print(cerere.getPrioritate() + " - ");
            cerere.printCerere(printWriter);
            tempArray.add(cerere);
        }

        for (Cerere cerere : tempArray)
            this.cereriBirou.add(cerere);
    }

    public static Birou gasesteBirou(String tipBirou, ManagementPrimarie database) {
        switch (tipBirou) {
            case "elev":
                return database.birouElevi;
            case "angajat":
                return database.birouAngajati;
            case "entitate juridica":
                return database.birouEntitatiJuridice;
            case "pensionar":
                return database.birouPensionari;
            case "persoana":
                return database.birouPersoane;
            default:
        }
        return null;
    }

}
