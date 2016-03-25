package com.example.BostoenApp.DB;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.BostoenApp.R;

import java.text.ParseException;
import java.util.ArrayList;


/**
 * Created by david on 20/03/2016.
 */
public class Reeks {
    private Integer id;
    private String naam;
    private Integer eersteVraag;
    private CustomDate last_update;

    public Reeks() {

    }

    /**
     * @param id
     * @param naam
     * @param eersteVraag
     * @param last_update
     * @throws ParseException wordt opgegooid wanneer de string last_update een verkeerd formaat heeft
     */
    public Reeks(Integer id, String naam, int eersteVraag, CustomDate last_update) throws ParseException {
        this.id = id;
        this.naam = naam;
        this.eersteVraag = eersteVraag;
        this.last_update = new CustomDate();
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

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("id : " + id + "\n");
        sb.append("naam : " + naam + "\n");
        sb.append("eerste vraag : " + eersteVraag + "\n");
        sb.append("last update : " + last_update);

        return sb.toString();

    }
    /**
     * Created by david on 21/03/2016.
     */
    public static class ReeksAdapter extends BaseAdapter {
        Context mContext;
        ArrayList<Reeks> reeksen;
        LayoutInflater mInflater;

        public ReeksAdapter(Context c,ArrayList<Reeks> reeksen)
        {
            mContext=c;
            this.reeksen=reeksen;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return reeksen.size();
        }

        @Override
        public Object getItem(int position) {
            return reeksen.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.reeks_view_item,null);
            TextView reeksnaam = (TextView)view.findViewById(R.id.Reeksnaam);

            Reeks currentReeks=reeksen.get(position);
            reeksnaam.setText(currentReeks.getNaam());

            return view;
        }
    }


}
