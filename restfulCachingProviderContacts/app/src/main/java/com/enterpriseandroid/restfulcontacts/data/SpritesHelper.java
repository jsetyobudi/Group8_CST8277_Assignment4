package com.enterpriseandroid.restfulcontacts.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Helper class for database
 * @author Christian Matekete
 *
 */

public class SpritesHelper extends SQLiteOpenHelper {
    static final int VERSION = 2;

    static final String DB_FILE = "enterprisesprites.db";

    static final String TAB_SPRITES = "sprite";

    // pk
    public static final String COL_ID = "id";                   // long

    // sprite column data
    public static final String COL_COLOR = "color"; // string
    public static final String COL_DX = "dx"; // string
    public static final String COL_DY = "dy"; // string
    public static final String COL_PANEL_HEIGHT = "panelheight"; // string
    public static final String COL_PANEL_WIDTH = "panelwidth"; // string
    public static final String COL_X = "x"; // string
    public static final String COL_Y = "y"; // string


    // meta-data
    static final String COL_REMOTE_ID = "remoteId";      // string
    static final String COL_DELETED = "deleted";         // boolean (null or MARK)
    static final String COL_DIRTY = "dirty";             // boolean (null or MARK)
    static final String COL_SYNC = "sync";               // string


    public SpritesHelper(Context context) {
        super(context, DB_FILE, null, VERSION);
    }

    /**
     *
     * @param db
     * @return table
     * This is the method that will help us create the table with the use of
     * the SQL query
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TAB_SPRITES + "("
                        + COL_ID + " integer PRIMARY KEY AUTOINCREMENT,"
                        + COL_COLOR + " text,"
                        + COL_DX + " integer,"
                        + COL_DY + " integer,"
                        + COL_PANEL_HEIGHT + " integer,"
                        + COL_PANEL_WIDTH + " integer,"
                        + COL_X + " integer,"
                        + COL_Y + " integer,"
                        + COL_REMOTE_ID + " string UNIQUE,"
                        + COL_DELETED + " integer,"
                        + COL_DIRTY + " integer,"
                        + COL_SYNC + " string UNIQUE)");
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     * This method is created whenever a new table is created.
     * The old version is discarded and replaced by the new veersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try { db.execSQL("drop table " + TAB_SPRITES); }
        catch (SQLiteException e) { }
        onCreate(db);
    }
}