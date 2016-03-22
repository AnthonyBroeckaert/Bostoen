package com.example.BostoenApp;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DraftActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);

        //elementen uit activity oproepen
        ListView reeksenlijst = (ListView) findViewById(R.id.reeksen);
        TextView antwoorden= (TextView)findViewById(R.id.antwoordopties);
        ImageView imageone = (ImageView) findViewById(R.id.imageViewone);
        /**ImageView imagetwo = (ImageView) findViewById(R.id.imageViewtwo);
        ImageView imagethree = (ImageView) findViewById(R.id.imageViewthree);*/

        //een ArrayList met reeksen maken
        ArrayList<Reeks> reeksen = new ArrayList<>();
        reeksen.add(new Reeks(null,"1",3,"hhsj"));
        reeksen.add(new Reeks(null,"2",5,"hfaahsj"));
        reeksen.add(new Reeks(null, "3", 10, "hhsj"));


        //bitmap maken aan de hand van resource ( Res/Mipmap)
        Bitmap bitone = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.test1);
        Bitmap bittwo = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.test2);

        //een ArrayList met vragen maken
        ArrayList<Vraag> vragen = new ArrayList<>();
        vragen.add(new Vraag(null,"11","",bitone,"",11,true));
        vragen.add(new Vraag(null, "22", "", bittwo, "", 12, true));


        ArrayList<AntwoordOptie> antwoordOpties=new ArrayList<>();
        antwoordOpties.add(new AntwoordOptie(1,"aa","aa",0,0,true,""));
        antwoordOpties.add(new AntwoordOptie(1,"bb","bb",0,0,true,""));
        antwoordOpties.add(new AntwoordOptie(1,"cc","c",0,0,true,""));

        //een object aanmaken van de database klasse
        DataDBAdapter dataDBAdapter = new DataDBAdapter(this);
        //database openen
        dataDBAdapter.open();
        //alle tabellen aanmaken
        //dataDBAdapter.create();



        //alle reeksen toevoegen
        dataDBAdapter.addReeksen(reeksen);
        //alle vragen toevoegen
        dataDBAdapter.addVragen(vragen);
        //alle antwoordopties toevoegen
        dataDBAdapter.addAntwoordOpties(antwoordOpties);

        //Cursor om alle reeksen uit database te halen
        Cursor one= dataDBAdapter.getReeksen();
        //ArrayList met alle reeksen uit de database
        ArrayList<Reeks> antwoordenreeksen= dataDBAdapter.getReeksenFromCursor(one);
        for(Reeks reeks : antwoordenreeksen)
        {
            Log.d("antwoordreeks id",new Integer(reeks.getId()).toString());
        }
        //adapter van ListView met reeksen instellen
        reeksenlijst.setAdapter(new ReeksAdapter(getApplicationContext(),antwoordenreeksen));

        //Cursor om alle vragen op te halen
       Cursor two=dataDBAdapter.getVragen();
        //ArrayLIst met alle vragen
        ArrayList<Vraag> antwoordenvragen=dataDBAdapter.getVragenFromCursor(two);

        Cursor vraag = dataDBAdapter.getVraag(1);
        Vraag temp=dataDBAdapter.getVraagFromCursor(vraag);
        String a =temp.getTekst()+temp.getLast_update();
        Log.d("test enkele reeks",a);


        //Afbeelding van vraag weergeven
        //omdat we momenteel niet met een Listview werken kan je slechts een afbeelding weergeven
        imageone.setImageBitmap(antwoordenvragen.get(0).getImage());

        //cursor om alle antwoordopties op te halen
        Cursor three = dataDBAdapter.getAntwoordOpties();
        //ArrayList<AntwoordOptie> antwoordOptiesAntwoord=dataDBAdapter.getAntwoordOptiesFromCursor(three);
        /**StringBuffer sb = new StringBuffer();
        for(AntwoordOptie antwoordOptie :antwoordOptiesAntwoord )
        {
            sb.append(antwoordOptie.getAntwoordTekst()+"\n");
        }
        antwoorden.setText(sb.toString());*/

        dataDBAdapter.clear();
        dataDBAdapter.close();
    }


}
