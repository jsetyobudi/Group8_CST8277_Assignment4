package com.enterpriseandroid.restfulcontacts.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author Christian Matekete
 * The SpriteHelper class will define the table sprite
 * in the SQLite database.
 */

public final class SpritesContract {
    //private, empty constructor
    private SpritesContract() {}



    public static final String TABLE = "sprite"; // the name of the table
    public static final String AUTHORITY = "com.enterpriseandroid.restfulsprites.SPRITES";

    //the definition of the URI; the table name is appended to it
    public static final Uri URI
            = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .appendPath(TABLE)
            .build();

    /** Contacts DIR type */
    public static final String CONTENT_TYPE_DIR
            = ContentResolver.CURSOR_DIR_BASE_TYPE
            + " + /vnd.com.enterpriseandroid.restfulsprites.sprite";

    /** Contacts ITEM type */
    public static final String CONTENT_TYPE_ITEM
            = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + " + /vnd.com.enterpriseandroid.restfulsprites.sprite";

    public static final int STATUS_OK = 0;
    public static final int STATUS_SYNC = 1;
    public static final int STATUS_DIRTY = 2;

    //Private class that defines each column of the table 'sprite'
    public static final class Columns implements BaseColumns {
        private Columns() {}

        public static final String ID = BaseColumns._ID;  // long
        public static final String COLOR = "color"; // string
        public static final String DX = "dx"; // string
        public static final String DY = "dy"; // string
        public static final String PANEL_HEIGHT = "panelheight"; // string
        public static final String PANEL_WIDTH = "panelwidth"; // string
        public static final String X = "x"; // string
        public static final String Y = "y"; // string

        public static final String STATUS = "status";


        // !!!
        // These columns really should not be exposed.
        public static final String SYNC = "sync";         // string!
        public static final String DIRTY = "dirty";       // int!
        public static final String REMOTE_ID = "rem";     // int!
    }
}