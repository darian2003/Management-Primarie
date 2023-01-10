package org.example;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class CommandLine {

    private CommandLine() {
    }

    static final String PERSOANA = "persoana";
    static final String ANGAJAT = "angajat";
    static final String ELEV = "elev";
    static final String ENTITATE_JURIDICA = "entitate juridica";
    static final String PENSIONAR = "pensionar";

    public static void adaugaUtilizator(String[] strTok, ManagementPrimarie database) {

        String userType = strTok[1];
        String nume = strTok[2];
        Utilizator utilizatorNou;

        // pentru fiecare utilizator nou adaugat asociem biroul specific instantei respective
        // facem acest lucru pentru usurarea implementarii functionalitatilor urmatoare
        switch (userType) {
            case PERSOANA:
                utilizatorNou = new Persoana(nume);
                utilizatorNou.setBirouUtilizator(database.birouPersoane);
                break;
            case ANGAJAT:
                String numeCompanie = strTok[3];
                utilizatorNou = new Angajat(nume, numeCompanie);
                utilizatorNou.setBirouUtilizator(database.birouAngajati);
                break;
            case PENSIONAR:
                utilizatorNou = new Pensionar(nume);
                utilizatorNou.setBirouUtilizator(database.birouPensionari);
                break;
            case ELEV:
                String scoala = strTok[3];
                utilizatorNou = new Elev(nume, scoala);
                utilizatorNou.setBirouUtilizator(database.birouElevi);
                break;
            case ENTITATE_JURIDICA:
                String reprezentant = strTok[3];
                utilizatorNou = new EntitiateJuridica(nume, reprezentant);
                utilizatorNou.setBirouUtilizator(database.birouEntitatiJuridice);
                break;
            default:
                return;
        }

        database.utilizatori.add(utilizatorNou);
    }

    public  static void cerereNoua(String[] strTok, ManagementPrimarie database, PrintWriter printWriter) throws CerereNepermisaExceptie {

        String numeUtilizator = strTok[1];
        Utilizator utilizator = Utilizator.gasesteUtilizatorDupaNume(numeUtilizator, database);

        if (utilizator == null) {
            return;
        }

        String tipCerere = strTok[2];
        String data = strTok[3];
        int prioritate = Integer.parseInt(strTok[4]);
        Cerere cerere = new Cerere(utilizator, prioritate, data);
        boolean ret = cerere.stabilireTipCerere(tipCerere);

        if (!ret)
            return;

        try {
            ret = cerere.verificaTipCerere();
        } catch (CerereNepermisaExceptie exceptie) {
            String tipUtilizator = utilizator.tipUtilizator(database);
            printWriter.println("Utilizatorul de tip " + tipUtilizator + " nu poate inainta o cerere de tip " + tipCerere);
            ret = false;
        }

        /* adaugam cererea atat in lista de cereri in asteptare ale utilizatorului respectiv
        cat si in coada de prioritate a birouli
         */
        if (ret) {
            utilizator.cereriInAsteptare.add(cerere);
            database.adaugaCerereInBirou(cerere);
        }

    }

    public static void retrageCerere(String[] strTok, ManagementPrimarie database) {

        String numeUtilizator = strTok[1];
        String data = strTok[2];
        Utilizator utilizator = Utilizator.gasesteUtilizatorDupaNume(numeUtilizator, database);

        // sterge cererea din biroul specific acestui tip de utilizator
        utilizator.retrageCerereDinBirou(numeUtilizator, data);

        // sterge cererea din ArrayList-ul cu cererile in asteptare depuse de acest utilizator
        for (Cerere cerere : utilizator.cereriInAsteptare) {
            if (cerere.getData().equals(data)) {
                utilizator.cereriInAsteptare.remove(cerere);
                break;
            }
        }

    }

    public static void afiseazaCereriInAsteptare(String[] strTok, ManagementPrimarie database, PrintWriter pw) {

        String numeUtilizator = strTok[1];
        pw.println(numeUtilizator + " - cereri in asteptare:");

        Utilizator utilizator = Utilizator.gasesteUtilizatorDupaNume(numeUtilizator, database);

        // sorteaza ArrayList-ul de utilizatori in functie de data pentru a respecta criteriul de afisare
        utilizator.cereriInAsteptare.sort(new ComparaCereriFunctieData());

        for (Cerere cerere : utilizator.cereriInAsteptare)
            cerere.printCerere(pw);
    }

    public static void afiseazaCereri(String tipUtilizator, ManagementPrimarie database, PrintWriter printWriter) {

        switch (tipUtilizator) {
            case ELEV:
                printWriter.print(ELEV);
                database.birouElevi.afiseazaCoadaCereriBirou(printWriter);
                break;
            case ANGAJAT:
                printWriter.print(ANGAJAT);
                database.birouAngajati.afiseazaCoadaCereriBirou(printWriter);
                break;
            case PENSIONAR:
                printWriter.print(PENSIONAR);
                database.birouPensionari.afiseazaCoadaCereriBirou(printWriter);
                break;
            case ENTITATE_JURIDICA:
                printWriter.print(ENTITATE_JURIDICA);
                database.birouEntitatiJuridice.afiseazaCoadaCereriBirou(printWriter);
                break;
            case PERSOANA:
                printWriter.print(PERSOANA);
                database.birouPersoane.afiseazaCoadaCereriBirou(printWriter);
                break;
            default:
        }
    }

    public static void afiseazaCereriFinalizate(String numeUtilizator, ManagementPrimarie database, PrintWriter pw) {

        pw.println(numeUtilizator + " - cereri in finalizate:");

        Utilizator utilizator = Utilizator.gasesteUtilizatorDupaNume(numeUtilizator, database);
        utilizator.cereriFinalizate.sort(new ComparaCereriFunctieData());

        for (Cerere cerere : utilizator.cereriFinalizate) {
            cerere.printCerere(pw);
        }
    }

    public static void adaugaFunctionarPublic(String[] strTok, ManagementPrimarie database) {

        String tipUtilizator = strTok[1];
        String numeFunctionar = strTok[2];

        FunctionarPublic functionarPublic = new FunctionarPublic(numeFunctionar);

        // adauga functionarul in lista biroului corespunzator
        switch (tipUtilizator) {
            case ELEV:
                database.birouElevi.functionari.add(functionarPublic);
                break;
            case ANGAJAT:
                database.birouAngajati.functionari.add(functionarPublic);
                break;
            case ENTITATE_JURIDICA:
                database.birouEntitatiJuridice.functionari.add(functionarPublic);
                break;
            case PENSIONAR:
                database.birouPensionari.functionari.add(functionarPublic);
                break;
            case PERSOANA:
                database.birouPersoane.functionari.add(functionarPublic);
                break;
            default:
        }

    }

    public static void rezolvaCerere(String[] strTok, ManagementPrimarie database) {
        String tipBirou = strTok[1];
        Birou birou = Birou.gasesteBirou(tipBirou, database);
        String numeFunctionar = strTok[2];

        /* scrie detaliile cererii in fisierul functionarului
        deoarece utilizez "PrintWriter" pentru output => daca fisierul este inexistent la
        momentul instantierii "pw", acesta va fi creat
         */
        String outputAntet = "src/main/resources/output/";
        FileOutputStream outputStream = null;
        PrintWriter pw = null;

        // append = true !
        try {
            outputStream = new FileOutputStream(outputAntet + "functionar_" + numeFunctionar + ".txt", true);
            pw = new PrintWriter(outputStream);
        } catch (FileNotFoundException e) {
            return;
        }

        /* elimin cererea prima cerere din birou si din lista de cereri de asteptare,
        o adaug in lista de finalizate si scriu in fisierul functionarului detaliile sale
         */
        Cerere cerere = (Cerere) birou.cereriBirou.poll();
        cerere.getUtilizator().cereriInAsteptare.remove(cerere);
        cerere.getUtilizator().cereriFinalizate.add(cerere);

        pw.println(cerere.getData() + " - " + cerere.getUtilizator().nume);
        pw.close();
    }



}
