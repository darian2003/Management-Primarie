package org.example;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public abstract class Utilizator {

    String nume;
    Birou birouUtilizator = new Birou();
    ArrayList<Cerere> cereriInAsteptare = new ArrayList<>();
    ArrayList<Cerere> cereriFinalizate = new ArrayList<>();

    // vom folosi acest setter pentru a asocia fiecarei instante care este utilizator biroul sau specific
    public void setBirouUtilizator(Birou birouUtilizator) {
        this.birouUtilizator = birouUtilizator;
    }

    protected Utilizator(String nume) {
        this.nume = nume;
    }

    // cauta in baza de date primita utilizatorul cu numele "nume" si il returneaza in cazul in care il gaseste
    public static Utilizator gasesteUtilizatorDupaNume(String nume, ManagementPrimarie database) {

        for(Utilizator utilizator : database.utilizatori)
            if (utilizator.nume.equals(nume))
                return utilizator;

        return null;
    }

    // seteaza tipul de utilizator aferent
    public String tipUtilizator(ManagementPrimarie database) {

        Utilizator utilizator = Utilizator.gasesteUtilizatorDupaNume(this.nume, database);
        if (utilizator instanceof Elev)
            return "elev";
        if (utilizator instanceof Angajat)
            return "angajat";
        if (utilizator instanceof Pensionar)
            return "pensionar";
        if (utilizator instanceof EntitiateJuridica)
            return "entitate juridica";
        if (utilizator instanceof Persoana)
            return "persoana";

        return null;
    }


    public void retrageCerereDinBirou(String numeUtilizator, String data) {

        /* pentru a nu pierde cererile din coada, atunci cand le scoatem cu "poll"
         le vom salva in acest ArrayList. La finalul cautarii, vom transfera inapoi
         in coada toate cererile din ArrayList
         */
        ArrayList<Cerere> tempArray = new ArrayList<>();

        // cautare
        while (!this.birouUtilizator.cereriBirou.isEmpty()) {
            Cerere tempCerere = (Cerere) this.birouUtilizator.cereriBirou.poll();
            // verific daca am gasit cererea pe care doresc sa o sterg
            if (!(numeUtilizator.equals(tempCerere.getUtilizator().nume) && data.equals(tempCerere.getData()))) {
                tempArray.add(tempCerere);
            }
        }

        for (Cerere tempCerere : tempArray)
            this.birouUtilizator.cereriBirou.add(tempCerere);

    }

    public abstract void printCerereUtilizator(PrintWriter pw, Cerere cerere);

}

    // clasa care implementeaza functia de comparare in functie de data
class ComparaCereriFunctieData implements Comparator<Cerere> {

    public int compare(Cerere cerere1, Cerere cerere2) {

        SimpleDateFormat formatareData1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        SimpleDateFormat formatareData2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = formatareData1.parse(cerere1.getData());
            date2 = formatareData2.parse(cerere2.getData());
        } catch (ParseException e) {
            return 0;
        }

        return date1.compareTo(date2);

    }

}
