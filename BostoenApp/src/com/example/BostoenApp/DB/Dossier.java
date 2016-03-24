package com.example.BostoenApp.DB;

/**
 * Created by david on 24/03/2016.
 */
public class Dossier {
    private Integer id;
    private Integer plaatsId;
    private String datum;
    private String naam;

    public Dossier()
    {

    }

    public Dossier(Integer id,Integer plaatsId,String datum,String naam)
    {
        this.setId(id);
        this.setPlaatsId(plaatsId);
        this.setDatum(datum);
        this.setNaam(naam);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlaatsId() {
        return plaatsId;
    }

    public void setPlaatsId(Integer plaatsId) {
        this.plaatsId = plaatsId;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
