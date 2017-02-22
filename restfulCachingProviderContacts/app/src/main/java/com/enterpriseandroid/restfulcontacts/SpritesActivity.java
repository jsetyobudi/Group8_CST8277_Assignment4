package com.enterpriseandroid.restfulcontacts;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.enterpriseandroid.restfulcontacts.data.SpritesContract;

/*
 * SpriteActivity class
 * Sprite activity class, originally by Stanley Pieda, edited by Johan
 * @author: Johan Setyobudi
 */
public class SpritesActivity extends BaseActivity
    implements LoaderManager.LoaderCallbacks<Cursor>
{
    private static final String TAG = "SPRITES";

    private static final int LOADER_ID = 42;

    private static final String[] PROJ = new String[] {
        SpritesContract.Columns.ID,
        SpritesContract.Columns.DX,
        SpritesContract.Columns.DY,
        SpritesContract.Columns.PANEL_HEIGHT,
        SpritesContract.Columns.PANEL_WIDTH,
        SpritesContract.Columns.X,
        SpritesContract.Columns.Y,
        SpritesContract.Columns.STATUS

    };

    private static final String[] FROM = new String[PROJ.length - 1];
    static { System.arraycopy(PROJ, 1, FROM, 0, FROM.length); }

    private static final int[] TO = new int[] {
            R.id.row_sprites_dx,
            R.id.row_sprites_status
    };
   /*
    * StatusBinder class
    */
    private static class StatusBinder
        implements SimpleCursorAdapter.ViewBinder
    {
        /*
         * initial constructor, empty
         */
        public StatusBinder() { }
        /*
         * setViewValue, setting the value of the view
         */
        @Override
        public boolean setViewValue(View view, Cursor cursor, int idx) {
            if (view.getId() != R.id.row_sprites_status) { return false; }
            setStatusBackground(cursor.getInt(idx), view);
            return true;
        }
    }


    private SimpleCursorAdapter listAdapter;
    /*
     * onCreateLoader method, attaching cursor
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
            this,
            SpritesContract.URI,
            PROJ,
            null,
            null,
            SpritesContract.Columns.DX + " ASC");
    }

    /*
     * onLoadFinished, swapping the cursors
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        listAdapter.swapCursor(cursor);
    }

    /*
     * onLoaderReset, when curor reset, null
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        listAdapter.swapCursor(null);
    }

    /*
     * onCreate method, first called when activity is started
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprites);

        ((Button) findViewById(R.id.activity_sprites_add)).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetails(null);
        } });

        listAdapter = new SimpleCursorAdapter(
            this,
            R.layout.sprite_row,
            null,
            FROM,
            TO,
            0);
        listAdapter.setViewBinder(new StatusBinder());

        ListView listView
            = ((ListView) findViewById(R.id.activity_sprites_list));
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int p, long id) {
                showDetails(p);
            } });

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }
    /*
     * showDetails class, showing the details, relative to position
     */
    void showDetails(int pos) {
        Cursor c = (Cursor) listAdapter.getItem(pos);
        showDetails(SpritesContract.URI.buildUpon()
            .appendPath(
                c.getString(c.getColumnIndex(SpritesContract.Columns.ID)))
            .build());
    }
    /*
     * showDetails class, adding sprite
     */
    void showDetails(Uri uri) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "adding sprite"); }
        Intent intent = new Intent();
        intent.setClass(this, SpriteDetailActivity.class);
        if (null != uri) {
            intent.putExtra(SpriteDetailActivity.KEY_URI, uri.toString());
        }
        startActivity(intent);
    }
}
