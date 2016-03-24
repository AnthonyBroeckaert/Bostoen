package com.example.BostoenApp.DB;

/**
 * Created by david on 24/03/2016.
 */
public class Plaats {
    private Integer id;
    private String adres;

    public Plaats()
    {

    }

    public Plaats(Integer id,String adres)
    {
        this.setId(id);
        this.setAdres(adres);

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }


}
