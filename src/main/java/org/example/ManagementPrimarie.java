package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class ManagementPrimarie {

    protected ArrayList<Utilizator> utilizatori = new ArrayList<>();

    protected Birou birouElevi = new Birou();
    protected Birou birouAngajati = new Birou();
    protected Birou birouPensionari = new Birou();
    protected Birou birouEntitatiJuridice = new Birou();
    protected Birou birouPersoane = new Birou();


    public static void main(String[] args) throws IOException {
        // Your code here
        String antetOutput = "src/main/resources/output/";
        String antetInput = "src/main/resources/input/";

        ManagementPrimarie database = new ManagementPrimarie();

        if(args == null)
        {
            return;
        }

        String fileName = args[0];
        File inputFile = new File(antetInput + fileName);
        File outputFile = new File(antetOutput + fileName);
        PrintWriter pw = new PrintWriter(outputFile);

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line = br.readLine();

            while (line != null) {
                String[] strTok = line.split("; ", 0);

                // LINIA DE COMANDA
                switch (strTok[0]) {
                    case "adauga_utilizator":
                        CommandLine.adaugaUtilizator(strTok, database);
                        break;
                    case "cerere_noua":
                        CommandLine.cerereNoua(strTok, database, pw);
                        break;
                    case "afiseaza_cereri_in_asteptare":
                        CommandLine.afiseazaCereriInAsteptare(strTok, database, pw);
                        break;
                    case "retrage_cerere":
                        CommandLine.retrageCerere(strTok, database);
                        break;
                    case "afiseaza_cereri":
                        String tipUtilizator = strTok[1];
                        CommandLine.afiseazaCereri(tipUtilizator, database, pw);
                        break;
                    case "adauga_functionar":
                        CommandLine.adaugaFunctionarPublic(strTok, database);
                        break;
                    case "rezolva_cerere":
                        CommandLine.rezolvaCerere(strTok, database);
                        break;
                    case "afiseaza_cereri_finalizate":
                        String numeUtilizator = strTok[1];
                        CommandLine.afiseazaCereriFinalizate(numeUtilizator, database, pw);
                        break;
                    default:
                }
                line = br.readLine();
            }
        } catch (IOException e) {

        } catch (CerereNepermisaExceptie e) {

        }

        pw.close();
    }

    // adauga cererea in coada biroului corespunzator
    public void adaugaCerereInBirou(Cerere cerere) {

        if (cerere.getUtilizator() instanceof Elev) {
            birouElevi.cereriBirou.add(cerere);
            return;
        }
        if (cerere.getUtilizator() instanceof Angajat) {
            birouAngajati.cereriBirou.add(cerere);
            return;
        }
        if (cerere.getUtilizator() instanceof Pensionar) {
            birouPensionari.cereriBirou.add(cerere);
            return;
        }
        if (cerere.getUtilizator() instanceof EntitiateJuridica) {
            birouEntitatiJuridice.cereriBirou.add(cerere);
            return;
        }
        if (cerere.getUtilizator() instanceof Persoana) {
            birouPersoane.cereriBirou.add(cerere);
        }

    }

}

    // clasa ce defineste functia de comparare pe care o vom folosi in coada cererilor cin clasa "Birou"
class ComparatorCerere implements Comparator<Cerere> {
    public int compare(Cerere cerere1, Cerere cerere2) {

        if (cerere1.getPrioritate() != cerere2.getPrioritate())
            return cerere2.getPrioritate() - cerere1.getPrioritate();
        return new ComparaCereriFunctieData().compare(cerere1, cerere2);
    }
}