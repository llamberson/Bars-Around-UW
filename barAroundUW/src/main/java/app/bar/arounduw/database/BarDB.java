package app.bar.arounduw.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import app.bar.arounduw.model.Bar;

/**
 * The Class BarDB represents the database for the bar.
 *
 * @author Ankit Sabhaya, Luke Lamberson
 * @version 1.0.1
 */
public class BarDB {

    // database constants --------------------------------------
    /** The constant db id. */
    public static final String DB_ID = "bar.db";

    /** The constant db version. */
    public static final int DB_VERSION = 1;

    // Favorite Bar table constants ---------------------------------
    /** The constant favorite bar table. */
    public static final String FAVORITE_BAR_TABLE = "favorite_bars";

    /** The constant bar name column. */
    public static final String NAME = "bar_name";

    /** The constant bar name column index. */
    public static final int NAME_COL = 0;

    /** The constant bar address column. */
    public static final String ADDRESS = "bar_address";

    /** The constant bar address column index. */
    public static final int ADDRESS_COL = 1;

    /** The constant bar phone number column. */
    public static final String PHONE_NUMBER = "bar_phone_number";

    /** The constant bar phone number column index. */
    public static final int PHONE_NUMBER_COL = 2;

    /** The constant bar about column. */
    public static final String ABOUT = "about_bar";

    /** The constant bar about column index. */
    public static final int ABOUT_COL = 3;

    /** The constant image name column. */
    public static final String IMAGE_NAME = "image_name";

    /** The constant image name column index. */
    public static final int IMAGE_NAME_COL = 4;

    /** The constant bar longitude column. */
    public static final String LONGITUDE = "bar_longitude";

    /** The constant bar longitude column index. */
    public static final int LONGITUDE_COL = 5;

    /** The constant bar latitude column. */
    public static final String LATITUDE = "bar_latitude";

    /** The constant bar latitude column index. */
    public static final int LATITUDE_COL = 6;


    //Database Statements ------------------------
    /** The constant query string to create the
     * favorite bar table.
     * */
    public static final String CREATE_FAVORITE_BAR_TABLE =
            "CREATE TABLE " + FAVORITE_BAR_TABLE + " (" +
                    NAME + " TEXT," +
                    ADDRESS + " TEXT," +
                    PHONE_NUMBER + " TEXT," +
                    ABOUT + " TEXT," +
                    IMAGE_NAME + " TEXT," +
                    LONGITUDE + " DOUBLE," +
                    LATITUDE + " DOUBLE" + ");";

    /** The constant query string to drop the favorite bar table. */
    public static final String DROP_FAVORITE_BAR_TABLE = "DROP TABLE IF EXISTS " + FAVORITE_BAR_TABLE;

    /** The constant query string to query from the favorite bar table. */
    public static final String QUERY_FAVORITE_BAR_TABLE = "SELECT * FROM " + FAVORITE_BAR_TABLE;


    /**
     * The Class DBHelper represents a db helper for the bar app.
     */
    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String ID, CursorFactory factory, int version) {
            super(context, ID, factory, version);
        }

        /**
         * This instantiates a new DB helper.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            // create table -----------------------------------
            db.execSQL(CREATE_FAVORITE_BAR_TABLE);
        }

        /**
         * On upgrade.
         *
         * @param db the db
         * @param oldVersion the old version
         * @param newVersion the new version
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_FAVORITE_BAR_TABLE);
            onCreate(db);
        }
    }


    // database object and database helper object
    /** The db. */
    private SQLiteDatabase db;

    /** The db helper. */
    private DBHelper dbHelper;

    // constructor
    /**
     * Instantiates a new bar db.
     *
     * @param context the context
     */
    public BarDB(Context context) {
        dbHelper = new DBHelper(context, DB_ID, null, DB_VERSION);
    }

    // private methods
    /**
     * Open readable db.
     */
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    /**
     * Open writeable db.
     */
    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    /**
     * Close db.
     */
    private void closeDB() {
        if (db != null)
            db.close();
    }


    //public methods ------------------------------------------

    /**
     * This gets the favorite bars.
     *
     * @return the favorite bars
     */
    public ArrayList<Bar> getFavoriteBars() {

        ArrayList<Bar> Bars = new ArrayList<>();

        this.openReadableDB();
        Cursor cursor;
        cursor = db.rawQuery(QUERY_FAVORITE_BAR_TABLE, null);

        while (cursor.moveToNext()) {

            Bar Bar = new Bar();
            Bar.NAME = cursor.getString(NAME_COL);
            Bar.ADDRESS = cursor.getString(ADDRESS_COL);
            Bar.PHONE_NUMBER = cursor.getString(PHONE_NUMBER_COL);
            Bar.ABOUT = cursor.getString(ABOUT_COL);
            Bar.IMAGE_NAME = cursor.getString(IMAGE_NAME_COL);
            Bar.LONGITUDE = cursor.getDouble(LONGITUDE_COL);
            Bar.LATITUDE = cursor.getDouble(LATITUDE_COL);

            Bars.add(Bar);
        }

        cursor.close();
        this.closeDB();

        return Bars;
    }

    /**
     * This delete favorite bar.
     *
     * @param Bar the bar to delete
     * @return the number of bars deleted
     */
    public int deleteFavoriteBar(Bar Bar) {

        this.openWriteableDB();
        return db.delete(FAVORITE_BAR_TABLE,
                NAME + " = ? AND " + ADDRESS + " = ? AND " + PHONE_NUMBER + " = ? " +
                        "AND  " + LONGITUDE + " = ? AND " + LATITUDE + " = ?",
                new String[]{Bar.NAME,
                        Bar.ADDRESS,
                        Bar.PHONE_NUMBER,
                        "" + Bar.LONGITUDE,
                        "" + Bar.LATITUDE,
                });
    }

    /**
     * This insert favorite bar.
     *
     * @param Bar the bar to insert
     * @return the id of the bar inserted
     */
    public long insertFavoriteBar(Bar Bar) {

        ContentValues cv = new ContentValues();

        cv.put(NAME, Bar.NAME);
        cv.put(ADDRESS, Bar.ADDRESS);
        cv.put(PHONE_NUMBER, Bar.PHONE_NUMBER);
        cv.put(ABOUT, Bar.ABOUT);
        cv.put(IMAGE_NAME, Bar.IMAGE_NAME);
        cv.put(LONGITUDE, Bar.LONGITUDE);
        cv.put(LATITUDE, Bar.LATITUDE);

        this.openWriteableDB();
        long rowID = db.insert(FAVORITE_BAR_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    /**
     * Empty favorite db.
     */
    public void emptyFavoriteDB() {
        this.openWriteableDB();
        db.execSQL(DROP_FAVORITE_BAR_TABLE);
        db.execSQL(CREATE_FAVORITE_BAR_TABLE);
        this.closeDB();
    }


    //add async activity
}
