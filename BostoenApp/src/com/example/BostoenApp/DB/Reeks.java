package com.example.BostoenApp.DB;

import java.text.ParseException;

/**
 * Created by david on 20/03/2016.
 */
public class Reeks {
    private Integer id;
    private String naam;
    private Integer eersteVraag;
    private CustomDate last_update;

    public Reeks()
    {

    }

    /**
     *
     * @param id
     * @param naam
     * @param eersteVraag
     * @param last_update
     * @throws ParseException wordt opgegooid wanneer de string last_update een verkeerd formaat heeft
     */
    public Reeks(Integer id,String naam,int eersteVraag,CustomDate last_update) throws ParseException {
        this.id=id;
        this.naam=naam;
        this.eersteVraag=eersteVraag;
        this.last_update=new CustomDate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Integer getEersteVraag() {
        return eersteVraag;
    }

    public void setEersteVraag(Integer eersteVraag) {
        this.eersteVraag = eersteVraag;
    }


    public CustomDate getLast_update() {
        return last_update;
    }

    public void setLast_update(CustomDate last_update) {
        this.last_update = last_update;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("id : "+id+"\n");
        sb.append("naam : "+naam+"\n");
        sb.append("eerste vraag : "+eersteVraag+"\n");
        sb.append("last update : "+last_update);

        return  sb.toString();

    }
}
