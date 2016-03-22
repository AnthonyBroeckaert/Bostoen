package com.example.BostoenApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by david on 21/03/2016.
 */
public class ReeksAdapter extends BaseAdapter{
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
