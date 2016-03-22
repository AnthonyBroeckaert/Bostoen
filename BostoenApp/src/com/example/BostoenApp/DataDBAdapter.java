package com.example.BostoenApp;

/**
 * Created by david on 20/03/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Ruben on 3/10/2016.
 * Databaseklasse die de opslag van data mogelijk maakt
 */

public class DataDBAdapter {
    /**naam van de database*/
    private static final String DATABASE_NAME = "BOSTOEN_DATABASE.db";

    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;
    public static String TAG = DataDBAdapter.class.getSimpleName();

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**declaratie van alle tabelnamen*/
    private static final String REEKS_TABLE = "REEKS";
    private static final String VRAAG_TABLE = "VRAAG";
    private static final String OPLOSSING_TABLE = "OPLOSSING";
    private static final String ANTWOORDOPTIE_TABLE = "ANTWOORDOPTIE";
    private static final String VRAGENDOSSIER_TABLE="VRAGENDOSSIER";
    private static final String PLAATS_TABLE="PLAATS";
    private static final String DOSSIER_TABLE="DOSSIER";


    //REEKS TABLE
    public static final String REEKS_ID = "id";
    public static final String REEKS_NAAM = "naam";
    public static final String REEKS_LAST_UPDATE = "last_update";
    public static final String REEKS_EERSTE_VRAAG = "eerste_vraag";

    private static final String[] REEKS_FIELDS = new String[] {
            REEKS_ID,
            REEKS_NAAM,
            REEKS_LAST_UPDATE,
            REEKS_EERSTE_VRAAG
    };
    //VRAAG TABLE
    public static final String VRAAG_ID = "id";
    public static final String VRAAG_TEKST = "naam";
    public static final String VRAAG_TIP = "tip";
    public static final String VRAAG_AFBEELDING = "afbeelding";
    public static final String VRAAG_LAST_UPDATE = "last_update";
    public static final String VRAAG_REEKS_ID = "reeks_id";
    public static final String VRAAG_GELDIG = "geldig";

    private static final String[] VRAAG_FIELDS = new String[] {
            VRAAG_ID,
            VRAAG_TEKST,
            VRAAG_TIP,
            VRAAG_AFBEELDING,
            VRAAG_LAST_UPDATE,
            VRAAG_REEKS_ID,
            VRAAG_GELDIG
    };

    //OPLOSSING TABLE
    public static final String OPLOSSING_ID = "id";
    public static final String OPLOSSING_TEKST = "tekst";
    public static final String OPLOSSING_GELDIG = "geldig";
    public static final String OPLOSSING_LAST_UPDATE = "last_update";

    private static final String[] OPLOSSING_FIELDS = new String[] {
            OPLOSSING_ID,
            OPLOSSING_TEKST,
            OPLOSSING_GELDIG,
            OPLOSSING_LAST_UPDATE
    };
    //ANTWOORDOPTIE TABLE
    public static final String ANTWOORDOPTIE_ID = "vraag_id";
    public static final String ANTWOORDOPTIE_ANTWOORD_TEKST = "antwoord_tekst";
    public static final String ANTWOORDOPTIE_ANTWOORD_OPMERKING = "antwoord_opmerking";
    public static final String ANTWOORDOPTIE_VOLGENDEVRAAG_ID = "volgendeVraag_id";
    public static final String ANTWOORDOPTIE_OPLOSSING_ID = "oplossing_id";
    public static final String ANTWOORDOPTIE_GELDIG = "geldig";
    public static final String ANTWOORDOPTIE_LAST_UPDATE = "last_update";

    private static final String[] ANTWOORDOPTIE_FIELDS = new String[] {
            ANTWOORDOPTIE_ID,
            ANTWOORDOPTIE_ANTWOORD_TEKST,
            ANTWOORDOPTIE_ANTWOORD_OPMERKING,
            ANTWOORDOPTIE_VOLGENDEVRAAG_ID,
            ANTWOORDOPTIE_OPLOSSING_ID,
            ANTWOORDOPTIE_GELDIG,
            ANTWOORDOPTIE_LAST_UPDATE
    };
    //PLAATS TABLE
    private static final String PLAATS_ID="id";
    private static final String PLAATS_ADRES="adres";

    private static final String[] PLAATS_FIELDS = new String[] {
            PLAATS_ID,
            PLAATS_ADRES
    };
    //DOSSIER TABLE
    private static final String DOSSIER_ID="id";
    private static final String DOSSIER_PLAATS_ID="plaats_id";
    private static final String DOSSIER_DATUM="datum";
    private static final String DOSSIER_NAAM="naam";

    private static final String[] DOSSIER_FIELDS = new String[] {
            ANTWOORDOPTIE_ID,
            ANTWOORDOPTIE_ANTWOORD_TEKST,
            ANTWOORDOPTIE_ANTWOORD_OPMERKING,
            ANTWOORDOPTIE_VOLGENDEVRAAG_ID,
            ANTWOORDOPTIE_OPLOSSING_ID,
            ANTWOORDOPTIE_GELDIG,
            ANTWOORDOPTIE_LAST_UPDATE
    };
    //VRAGENDOSSIER TABLE
    private static final String VRAGENDOSSIER_DOSSIER_NR="dossiernr";
    private static final String VRAGENDOSSIER_VRAAG_ID="vraag_id";
    private static final String VRAGENDOSSIER_ANTWOORD_TEKST="antwoord_tekst";

    private static final String[] VRAGENDOSSIER_FIELDS = new String[] {
            VRAGENDOSSIER_DOSSIER_NR,
            VRAGENDOSSIER_VRAAG_ID,
            VRAGENDOSSIER_ANTWOORD_TEKST
    };

    /**strings met sql statement om tabellen aan te maken*/
    private static final String CREATE_TABLE_REEKS =
            "create table " + REEKS_TABLE + "("
                    + REEKS_ID + " INTEGER PRIMARY KEY,"
                    + REEKS_NAAM + " TEXT NOT NULL UNIQUE,"
                    + REEKS_LAST_UPDATE +" TEXT NOT NULL,"
                    + REEKS_EERSTE_VRAAG + " INTEGER"
                    + ");";

    private static final String CREATE_TABLE_VRAAG =
            "create table " + VRAAG_TABLE + "("
                    + VRAAG_ID + " INTEGER PRIMARY KEY,"
                    + VRAAG_TEKST + " TEXT NOT NULL,"
                    + VRAAG_TIP +" TEXT,"
                    + VRAAG_AFBEELDING + " BLOB,"
                    + VRAAG_LAST_UPDATE + " TEXT NOT NULL,"
                    + VRAAG_REEKS_ID + " INTEGER NOT NULL,"
                    + VRAAG_GELDIG + " INTEGER NOT NULL"
                    + ");";

    private static final String CREATE_TABLE_OPLOSSING =
            "create table " + OPLOSSING_TABLE + "("
                    + OPLOSSING_ID + " INTEGER PRIMARY KEY,"
                    + OPLOSSING_TEKST + " TEXT NOT NULL,"
                    + OPLOSSING_GELDIG +" TEXT NOT NULL,"
                    + OPLOSSING_LAST_UPDATE + "TEXT"
                    + ");";
    private static final String CREATE_TABLE_ANTWOORDOPTIE =
            "create table " + ANTWOORDOPTIE_TABLE + "("
                    + ANTWOORDOPTIE_ID+" INTEGER,"
                    + ANTWOORDOPTIE_ANTWOORD_TEKST+" TEXT,"
                    + ANTWOORDOPTIE_ANTWOORD_OPMERKING + " TEXT,"
                    + ANTWOORDOPTIE_VOLGENDEVRAAG_ID + " INTEGER,"
                    + ANTWOORDOPTIE_OPLOSSING_ID + " INTEGER,"
                    + ANTWOORDOPTIE_GELDIG +" TEXT NOT NULL,"
                    + ANTWOORDOPTIE_LAST_UPDATE + " TEXT,"
                    +"PRIMARY KEY ("+ANTWOORDOPTIE_ID+","+ANTWOORDOPTIE_ANTWOORD_TEKST+")"
                    + ");";

    private static final String CREATE_TABLE_PLAATS=
            "create table " + PLAATS_TABLE +"("
                    + PLAATS_ID +" INTEGER PRIMARY KEY,"
                    + PLAATS_ADRES+" text NOT NULL"+");";
    private static final String CREATE_TABLE_DOSSIER=
            "create table "+ DOSSIER_TABLE+"("
                    + DOSSIER_ID +" INTEGER PRIMARY KEY,"
                    + DOSSIER_PLAATS_ID +" INTEGER,"
                    + DOSSIER_DATUM +" text NOT NULL,"
                    + DOSSIER_NAAM +" text NOT NULL"+");";
    private static final String CREATE_TABLE_VRAGENDOSSIER=
            "create table "+ VRAGENDOSSIER_TABLE+"("
                    + VRAGENDOSSIER_DOSSIER_NR + " INTEGER PRIMARY KEY,"
                    + VRAGENDOSSIER_VRAAG_ID + " INTEGER,"
                    + VRAGENDOSSIER_ANTWOORD_TEKST+" text"+");";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE_REEKS);
            db.execSQL(CREATE_TABLE_ANTWOORDOPTIE);
            db.execSQL(CREATE_TABLE_DOSSIER);
            db.execSQL(CREATE_TABLE_OPLOSSING);
            db.execSQL(CREATE_TABLE_PLAATS);
            db.execSQL(CREATE_TABLE_VRAGENDOSSIER);
            db.execSQL(CREATE_TABLE_VRAAG);



        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "upgrading database ...");
            db.execSQL("DROP TABLE IF EXISTS " + REEKS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ANTWOORDOPTIE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DOSSIER_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + OPLOSSING_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + PLAATS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + VRAGENDOSSIER_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + VRAAG_TABLE);
            onCreate(db);
        }
    }
    public void create()
    {
        mDb.execSQL(CREATE_TABLE_REEKS);
        mDb.execSQL(CREATE_TABLE_ANTWOORDOPTIE);
        mDb.execSQL(CREATE_TABLE_DOSSIER);
        mDb.execSQL(CREATE_TABLE_OPLOSSING);
        mDb.execSQL(CREATE_TABLE_PLAATS);
        mDb.execSQL(CREATE_TABLE_VRAGENDOSSIER);
        mDb.execSQL(CREATE_TABLE_VRAAG);
    }

    public void clear()
    {
        mDb.execSQL("DROP TABLE IF EXISTS " + REEKS_TABLE);
        mDb.execSQL("DROP TABLE IF EXISTS " + ANTWOORDOPTIE_TABLE);
        mDb.execSQL("DROP TABLE IF EXISTS " + DOSSIER_TABLE);
        mDb.execSQL("DROP TABLE IF EXISTS " + OPLOSSING_TABLE);
        mDb.execSQL("DROP TABLE IF EXISTS " + PLAATS_TABLE);
        mDb.execSQL("DROP TABLE IF EXISTS " + VRAGENDOSSIER_TABLE);
        mDb.execSQL("DROP TABLE IF EXISTS " + VRAAG_TABLE);
    }
    public DataDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    //PieDbAdapter Open, Close, Upgrade
    public DataDBAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    /**
     *
     */
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    /**
     *
     * @throws SQLException
     */
    public void upgrade() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx); //open
        mDb = mDbHelper.getWritableDatabase();
        mDbHelper.onUpgrade(mDb, 1, 0);
    }


    /**reeks toevoegen aan database
     *
     * @param reeks
     * het id in het opgegeven Reeks object heeft geen belang
     */
    public void addReeks(Reeks reeks)
    {
        ContentValues initialValues=new ContentValues();
        initialValues.put(REEKS_NAAM,reeks.getNaam());
        initialValues.put(REEKS_EERSTE_VRAAG,reeks.getEersteVraag());
        initialValues.put(REEKS_LAST_UPDATE,reeks.getLast_update());

        mDb.insert(REEKS_TABLE, null, initialValues);
    }

    /**
     * een ArrayList met reeksen toevoegen aan de database
     * @param reeksen
     * de opgegeven id's zijn niet van belang
     */
    public void addReeksen(ArrayList<Reeks> reeksen)
    {
        for(Reeks reeks:reeksen)
        {
            addReeks(reeks);
        }
    }

    /**
     * @return een Cursor met alle reeksen
     */
    public Cursor getReeksen() {
        //eerste null is de 'where', dan 'selectionArgs' 'GroupBy' 'Having' 'orderBy' en 'limit'
        return mDb.query(REEKS_TABLE, REEKS_FIELDS, null, null, null, null, null);
    }

    /**
     * @param cursor wordt verkregen door getReeksen() op te roepen
     * @return een ArrayList met alle reeksen (null indien de tabel leeg is)
     */
    public static ArrayList<Reeks> getReeksenFromCursor(Cursor cursor)
    {
        if(cursor.getCount()>0)
        {
            ArrayList<Reeks> output = new ArrayList<>();
            while (cursor.moveToNext()) {
                Reeks reeks = new Reeks();
                reeks.setId(cursor.getInt(cursor.getColumnIndex(REEKS_ID)));
                reeks.setEersteVraag(cursor.getInt(cursor.getColumnIndex(REEKS_EERSTE_VRAAG)));
                reeks.setNaam(cursor.getString(cursor.getColumnIndex(REEKS_NAAM)));
                reeks.setLast_update(cursor.getString(cursor.getColumnIndex(REEKS_LAST_UPDATE)));
                output.add(reeks);
            }

            return output;
        }
        else
        {
            return null;
        }

    }

    /**
     *
     * @param id het id van het gewenste Reeks object
     * @return  een Cursor met de opgegeven reeks
     */
    public Cursor getReeks(int id)
    {
        String[] selectionArgs = {String.valueOf(id)};
        //eerste null is de 'where', dan 'selectionArgs' 'GroupBy' 'Having' 'orderBy' en 'limit'
        return mDb.query(REEKS_TABLE, REEKS_FIELDS,REEKS_ID+"=?",selectionArgs, null, null, null, null);
    }

    /**
     * @param cursor wordt verkregen door getReeks() op te roepen
     * @return het gevraagde Reeks object (indien null werd het niet gevonden)
     */
    public Reeks getReeksFromCursor(Cursor cursor)
    {
        if(cursor.getCount()>0 &&  cursor!=null && cursor.moveToFirst())
        {
            Reeks reeks = new Reeks();
            reeks.setId(cursor.getInt(cursor.getColumnIndex(REEKS_ID)));
            reeks.setEersteVraag(cursor.getInt(cursor.getColumnIndex(REEKS_EERSTE_VRAAG)));
            reeks.setNaam(cursor.getString(cursor.getColumnIndex(REEKS_NAAM)));
            reeks.setLast_update(cursor.getString(cursor.getColumnIndex(REEKS_LAST_UPDATE)));

            return reeks;
        }
        else
        {
            return null;
        }

    }

    /**
     * Methode om een vraag toe te voegen aan de database
     * @param vraag
     * het opgegeven id is niet van belang
     */
    public void  addVraag(Vraag vraag)
    {
        ContentValues initialValues=new ContentValues();
        initialValues.put(VRAAG_TEKST,vraag.getTekst());
        initialValues.put(VRAAG_TIP,vraag.getTip());
        initialValues.put(VRAAG_REEKS_ID,vraag.getReeks_id());
        /**boolean naar int*/
        if(vraag.isGeldig())
        {
            initialValues.put(VRAAG_GELDIG, 1);
        }
        else
        {
            initialValues.put(VRAAG_GELDIG, 0);
        }
        initialValues.put(VRAAG_LAST_UPDATE,vraag.getLast_update());

        /**Bitmap naar BLOB converteren*/
        Bitmap bmp=vraag.getImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        initialValues.put(VRAAG_AFBEELDING, byteArray);

        mDb.insert(VRAAG_TABLE, null, initialValues);


    }

    /**
     * @param vragen ArrayList met alle vragen die je wilt toevoegen
     * de opgegeven id's zijn niet van belang
     */
    public void addVragen(ArrayList<Vraag> vragen)
    {
        for(Vraag vraag:vragen)
        {
            addVraag(vraag);
        }
    }

    /**
     * @return een Cursor met alle vragen
     */
    public Cursor getVragen() {
        return mDb.query(VRAAG_TABLE, VRAAG_FIELDS, null, null, null, null, null);
    }

    /**
     * @param cursor wordt verkregen door getVragen op te roepen
     * @return een ArrayList met alle vragen (null indien de tabel leeg is)
     */
    public ArrayList<Vraag> getVragenFromCursor(Cursor cursor)
    {
        if(cursor.getCount()>0)
        {
            ArrayList<Vraag> output = new ArrayList<>();
            while (cursor.moveToNext()) {
                Vraag vraag=new Vraag();
                vraag.setId(cursor.getInt(cursor.getColumnIndex(VRAAG_ID)));
                vraag.setTekst(cursor.getString(cursor.getColumnIndex(VRAAG_TEKST)));
                vraag.setLast_update(cursor.getString(cursor.getColumnIndex(VRAAG_LAST_UPDATE)));
                vraag.setReeks_id(cursor.getInt(cursor.getColumnIndex(VRAAG_REEKS_ID)));
                vraag.setGeldig(cursor.getInt(cursor.getColumnIndex(VRAAG_GELDIG)) == 1);

                byte[] byteArray = cursor.getBlob(cursor.getColumnIndex(VRAAG_AFBEELDING));
                Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                vraag.setImage(image);

                output.add(vraag);
            }
            return output;
        }
        else {
            return null;
        }

    }

    /**
     *
     * @param id het id van het gewenste Vraag object
     * @return  een Cursor met de opgegeven vraag
     */
    public Cursor getVraag(int id)
    {
        String[] selectionArgs = {String.valueOf(id)};
        //eerste null is de 'where', dan 'selectionArgs' 'GroupBy' 'Having' 'orderBy' en 'limit'
        return mDb.query(VRAAG_TABLE, VRAAG_FIELDS,VRAAG_ID+"=?",selectionArgs, null, null, null, null);
    }

    /**
     *
     * @param cursor wordt verkregen door getVraag() op te roepen
     * @return het gevraagde Vraag object (indien null werd het niet gevonden)
     */
    public Vraag getVraagFromCursor(Cursor cursor)
    {
        if(cursor.getCount()>0 &&  cursor!=null && cursor.moveToFirst())
        {
            Vraag vraag=new Vraag();
            vraag.setId(cursor.getInt(cursor.getColumnIndex(VRAAG_ID)));
            vraag.setTekst(cursor.getString(cursor.getColumnIndex(VRAAG_TEKST)));
            vraag.setLast_update(cursor.getString(cursor.getColumnIndex(VRAAG_LAST_UPDATE)));
            vraag.setReeks_id(cursor.getInt(cursor.getColumnIndex(VRAAG_REEKS_ID)));
            vraag.setGeldig(cursor.getInt(cursor.getColumnIndex(VRAAG_GELDIG)) == 1);

            byte[] byteArray = cursor.getBlob(cursor.getColumnIndex(VRAAG_AFBEELDING));
            Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            vraag.setImage(image);
            return vraag;
        }
        else
        {
            return null;
        }

    }

    /**
     *methode om een antwoordoptie toe te voegen aan de database
     * @param antwoordOptie
     * het opgegeven id is niet van belang
     */
    public void addAntwoordOptie(AntwoordOptie antwoordOptie)
    {
        ContentValues initialValues=new ContentValues();
        initialValues.put(ANTWOORDOPTIE_ID, antwoordOptie.getVraagId());
        initialValues.put(ANTWOORDOPTIE_ANTWOORD_TEKST, antwoordOptie.getAntwoordTekst());
        initialValues.put(ANTWOORDOPTIE_ANTWOORD_OPMERKING, antwoordOptie.getAntwoordOpmerking());
        initialValues.put(ANTWOORDOPTIE_OPLOSSING_ID, antwoordOptie.getOplossing());
        initialValues.put(ANTWOORDOPTIE_VOLGENDEVRAAG_ID, antwoordOptie.getVolgendeVraag());
        initialValues.put(ANTWOORDOPTIE_LAST_UPDATE, antwoordOptie.getLast_update());
        if(antwoordOptie.isGeldig())
        {
            initialValues.put(ANTWOORDOPTIE_GELDIG, 1);
        }
        else
        {
            initialValues.put(ANTWOORDOPTIE_GELDIG,0);
        }
    }

    /**
     * bug
     * methode om antwoordopties toe te voegen aan de database
     * @param antwoordOpties
     * de opgegeven id's zijn niet van belang
     */
    public void addAntwoordOpties(ArrayList<AntwoordOptie> antwoordOpties)
    {
        for(AntwoordOptie antwoordOptie : antwoordOpties)
        {
            addAntwoordOptie(antwoordOptie);
        }
    }

    /**
     * @return een Cursor met alle antwoordopties
     */
    public Cursor getAntwoordOpties()
    {
        return mDb.query(ANTWOORDOPTIE_TABLE, ANTWOORDOPTIE_FIELDS, null, null, null, null, null);
    }

    /**
     *bug
     * @param cursor wordt verkregen door getAntwoordOpties() op te roepen
     * @return een ArrayList met alle AntwoordOpties (null indien de tabel leeg is)
     */
    public ArrayList<AntwoordOptie> getAntwoordOptiesFromCursor(Cursor cursor)
    {
        if(cursor.getCount()>0)
        {
            ArrayList<AntwoordOptie> output = new ArrayList<>();

            while (cursor.moveToNext())
            {
                AntwoordOptie antwoordOptie = new AntwoordOptie();

                antwoordOptie.setVraagId(cursor.getInt(cursor.getColumnIndex(ANTWOORDOPTIE_ID)));
                antwoordOptie.setAntwoordTekst(cursor.getString(cursor.getColumnIndex(ANTWOORDOPTIE_ANTWOORD_TEKST)));
                antwoordOptie.setAntwoordOpmerking(cursor.getString(cursor.getColumnIndex(ANTWOORDOPTIE_ANTWOORD_OPMERKING)));
                antwoordOptie.setOplossing(cursor.getInt(cursor.getColumnIndex(ANTWOORDOPTIE_OPLOSSING_ID)));
                antwoordOptie.setVolgendeVraag(cursor.getInt(cursor.getColumnIndex(ANTWOORDOPTIE_VOLGENDEVRAAG_ID)));
                antwoordOptie.setGeldig(cursor.getInt(cursor.getColumnIndex(ANTWOORDOPTIE_GELDIG))==1);
                antwoordOptie.setLast_update(cursor.getString(cursor.getColumnIndex(ANTWOORDOPTIE_LAST_UPDATE)));

                output.add(antwoordOptie);
            }
            return output;
        }
        else {
            return null;
        }

    }
    /**
     *
     * @param vraagid
     * @return een Cursor met de Antwoordopties van de opgegeven vraag
     */
    public Cursor getAntwoordOptiesVraag(int vraagid)
    {
        String[] selectionArgs = {String.valueOf(vraagid)};
        //eerste null is de 'where', dan 'selectionArgs' 'GroupBy' 'Having' 'orderBy' en 'limit'
        return mDb.query(ANTWOORDOPTIE_TABLE, ANTWOORDOPTIE_FIELDS,ANTWOORDOPTIE_ID+"=?",selectionArgs, null, null, null, null);
    }

    /**
     *
     * @param cursor wordt verkregen door  getAntwoordOptiesVraag(int vraagid) op te roepen (indien null werd het niet gevonden)
     * @return de gevraagde AntwoordOpties van de vraag (null indien het niet werd gevonden)
     */
    public ArrayList<AntwoordOptie> getAntwoordOptiesVraagFromCursor(Cursor cursor)
    {
        Log.d("getcount",new Integer(cursor.getCount()).toString());
        if(cursor.getCount()>0)
        {
            ArrayList<AntwoordOptie> output = new ArrayList<>();

            while (cursor.moveToNext())
            {
                AntwoordOptie antwoordOptie = new AntwoordOptie();

                antwoordOptie.setVraagId(cursor.getInt(cursor.getColumnIndex(ANTWOORDOPTIE_ID)));
                antwoordOptie.setAntwoordTekst(cursor.getString(cursor.getColumnIndex(ANTWOORDOPTIE_ANTWOORD_TEKST)));
                antwoordOptie.setAntwoordOpmerking(cursor.getString(cursor.getColumnIndex(ANTWOORDOPTIE_ANTWOORD_OPMERKING)));
                antwoordOptie.setOplossing(cursor.getInt(cursor.getColumnIndex(ANTWOORDOPTIE_OPLOSSING_ID)));
                antwoordOptie.setVolgendeVraag(cursor.getInt(cursor.getColumnIndex(ANTWOORDOPTIE_VOLGENDEVRAAG_ID)));
                antwoordOptie.setGeldig(cursor.getInt(cursor.getColumnIndex(ANTWOORDOPTIE_GELDIG))==1);
                antwoordOptie.setLast_update(cursor.getString(cursor.getColumnIndex(ANTWOORDOPTIE_LAST_UPDATE)));

                output.add(antwoordOptie);
            }
            return output;
        }
        else {
            return null;
        }

    }


}