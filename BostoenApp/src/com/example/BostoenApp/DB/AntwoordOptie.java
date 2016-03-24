package com.example.BostoenApp.DB;

/**
 * Created by david on 21/03/2016.
 */
public class AntwoordOptie {
    private int vraagId;
    private String antwoordTekst;
    private String antwoordOpmerking;
    private int volgendeVraag;
    private int oplossing;
    private boolean geldig;
    private String last_update;

    public AntwoordOptie()
    {

    }

    public AntwoordOptie(int vraagId,String antwoordTekst,String antwoordOpmerking,int volgendeVraag,int oplossing,boolean geldig,String last_update)
    {
        this.setVraagId(vraagId);
        this.setAntwoordTekst(antwoordTekst);
        this.setAntwoordOpmerking(antwoordOpmerking);
        this.setVolgendeVraag(volgendeVraag);
        this.setOplossing(oplossing);
        this.setGeldig(geldig);
        this.setLast_update(last_update);
    }


    public String getAntwoordTekst() {
        return antwoordTekst;
    }

    public void setAntwoordTekst(String antwoordTekst) {
        this.antwoordTekst = antwoordTekst;
    }

    public String getAntwoordOpmerking() {
        return antwoordOpmerking;
    }

    public void setAntwoordOpmerking(String antwoordOpmerking) {
        this.antwoordOpmerking = antwoordOpmerking;
    }

    public int getVolgendeVraag() {
        return volgendeVraag;
    }

    public void setVolgendeVraag(int volgendeVraag) {
        this.volgendeVraag = volgendeVraag;
    }

    public int getOplossing() {
        return oplossing;
    }

    public void setOplossing(int oplossing) {
        this.oplossing = oplossing;
    }

    public boolean isGeldig() {
        return geldig;
    }

    public void setGeldig(boolean geldig) {
        this.geldig = geldig;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public int getVraagId() {
        return vraagId;
    }

    public void setVraagId(int vraagId) {
        this.vraagId = vraagId;
    }
}
