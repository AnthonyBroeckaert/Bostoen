package com.example.BostoenApp.DB;

/**
 * Created by david on 24/03/2016.
 */
public class VragenDossier {
    private String  vraagTekst;
    private String  antwoordTekst;
    private Integer dossierNr;

    public VragenDossier()
    {

    }

    public VragenDossier(String vraagTekst,String antwoordTekst)
    {
       this.setVraagTekst(vraagTekst);
        this.setAntwoordTekst(antwoordTekst);
    }


    public String getVraagTekst() {
        return vraagTekst;
    }

    public void setVraagTekst(String vraagTekst) {
        this.vraagTekst = vraagTekst;
    }

    public String getAntwoordTekst() {
        return antwoordTekst;
    }

    public void setAntwoordTekst(String antwoordTekst) {
        this.antwoordTekst = antwoordTekst;
    }

    public Integer getDossierNr() {
        return dossierNr;
    }

    public void setDossierNr(Integer dossierNr) {
        this.dossierNr = dossierNr;
    }
}
