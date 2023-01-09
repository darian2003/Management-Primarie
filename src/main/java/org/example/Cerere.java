package org.example;

import java.io.PrintWriter;

public class Cerere {

    enum TipCerere {
        INLOCUIRE_BULETIN,
        INREGISTRARE_VENIT_SALARIAL,
        INLOCUIRE_CARNET_DE_SOFER,
        INLOCUIRE_CARNET_DE_ELEV,
        CREARE_ACT_CONSTITUTIV,
        REINNOIRE_AUTORIZATIE,
        INREGISTRARE_CUPOANE_DE_PENSIE,
        INVALID
    }

    private TipCerere tipCerere;
    private Utilizator utilizator;
    private int prioritate;
    private String data;

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public Cerere(Utilizator utilizator, int prioritate, String data) {
        this.utilizator = utilizator;
        this.prioritate = prioritate;
        this.data = data;
    }

    public int getPrioritate() {
        return prioritate;
    }

    public String getData() {
        return data;
    }

    public boolean stabilireTipCerere(String cerere) {

        tipCerere = TipCerere.INVALID;

        switch (cerere) {
            case "inlocuire buletin":
                this.tipCerere = TipCerere.INLOCUIRE_BULETIN;
                break;
            case "inregistrare venit salarial":
                this.tipCerere = TipCerere.INREGISTRARE_VENIT_SALARIAL;
                break;
            case "inlocuire carnet de sofer":
                this.tipCerere = TipCerere.INLOCUIRE_CARNET_DE_SOFER;
                break;
            case "inlocuire carnet de elev":
                this.tipCerere = TipCerere.INLOCUIRE_CARNET_DE_ELEV;
                break;
            case "creare act constitutiv":
                this.tipCerere = TipCerere.CREARE_ACT_CONSTITUTIV;
                break;
            case "reinnoire autorizatie":
                this.tipCerere = TipCerere.REINNOIRE_AUTORIZATIE;
                break;
            case "inregistrare cupoane de pensie":
                this.tipCerere = TipCerere.INREGISTRARE_CUPOANE_DE_PENSIE;
                break;
            default:
        }

        if (tipCerere == TipCerere.INVALID) {
            return false;
        }

        return true;

    }

    public String convertTypeToString() {
        switch (this.tipCerere) {
            case INLOCUIRE_BULETIN:
                return "inlocuire buletin";
            case INLOCUIRE_CARNET_DE_ELEV:
                return "inlocuire carnet de elev";
            case INLOCUIRE_CARNET_DE_SOFER:
                return "inlocuire carnet de sofer";
            case INREGISTRARE_CUPOANE_DE_PENSIE:
                return "inregistrare cupoane de pensie";
            case INREGISTRARE_VENIT_SALARIAL:
                return "inregistrare venit salarial";
            case REINNOIRE_AUTORIZATIE:
                return "reinnoire autorizatie";
            case CREARE_ACT_CONSTITUTIV:
                return "creare act constitutiv";
            default:
                return null;
        }
    }


    public void printCerere(PrintWriter pw) {

        if (this.utilizator instanceof Angajat) {
            Angajat angajat = (Angajat) utilizator;
            angajat.printCerereUtilizator(pw, this);
            return;
        }
        if (this.utilizator instanceof Elev) {
            Elev elev = (Elev) utilizator;
            elev.printCerereUtilizator(pw, this);
            return;
        }
        if (this.utilizator instanceof EntitiateJuridica) {
            EntitiateJuridica entitiateJuridica = (EntitiateJuridica) utilizator;
            entitiateJuridica.printCerereUtilizator(pw, this);
            return;
        }
        if (this.utilizator instanceof Persoana) {
            Persoana persoana = (Persoana) utilizator;
            persoana.printCerereUtilizator(pw, this);
            return;
        }
        if (this.utilizator instanceof Pensionar pensionar) {
            pensionar.printCerereUtilizator(pw, this);
        }

    }

    public boolean verificaTipCerere() throws CerereNepermisaExceptie {

        final String UTILIZATOR_INVALID = "Utilizatorul nu poate inainta aceasta cerere";

            switch(this.tipCerere) {
                case INLOCUIRE_BULETIN:
                    if (!(this.utilizator instanceof InlocuireBuletin))
                        throw new CerereNepermisaExceptie(UTILIZATOR_INVALID);
                        break;
                case INLOCUIRE_CARNET_DE_SOFER:
                    if (!(this.utilizator instanceof InlocuireCarnetDeSofer))
                        throw new CerereNepermisaExceptie(UTILIZATOR_INVALID);
                        break;
                case INLOCUIRE_CARNET_DE_ELEV:
                    if (!(this.utilizator instanceof InlocuireCarnetDeElev))
                        throw new CerereNepermisaExceptie(UTILIZATOR_INVALID);
                        break;
                case INREGISTRARE_VENIT_SALARIAL:
                    if (!(this.utilizator instanceof InregistrareVenitSalarial))
                        throw new CerereNepermisaExceptie(UTILIZATOR_INVALID);
                        break;
                case REINNOIRE_AUTORIZATIE:
                    if (!(this.utilizator instanceof ReinnoireAutorizatie))
                        throw new CerereNepermisaExceptie(UTILIZATOR_INVALID);
                        break;
                case CREARE_ACT_CONSTITUTIV:
                    if (!(this.utilizator instanceof CreareActConstitutiv))
                        throw new CerereNepermisaExceptie(UTILIZATOR_INVALID);
                        break;
                case INREGISTRARE_CUPOANE_DE_PENSIE:
                    if (!(this.utilizator instanceof InregistrareCupoaneDePensie))
                        throw new CerereNepermisaExceptie(UTILIZATOR_INVALID);
                        break;
                default:
            }
        return true;
    }


}
