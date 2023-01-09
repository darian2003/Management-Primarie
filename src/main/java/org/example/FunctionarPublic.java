package org.example;

public class FunctionarPublic {

    private String nume;

    public String getNume() {
        return nume;
    }

    public FunctionarPublic(String nume) {
        this.nume = nume;
    }

    public static FunctionarPublic gasesteFunctionarDupaNume(String nume, Birou birou) {
        for(FunctionarPublic functionarPublic : birou.functionari) {
            if (functionarPublic.getNume().equals(nume))
                return functionarPublic;
        }
        return null;
    }


}
