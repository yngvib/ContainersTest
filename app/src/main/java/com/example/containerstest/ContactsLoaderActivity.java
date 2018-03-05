package com.example.containerstest;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class ContactsLoaderActivity extends ListActivity
    implements LoaderManager.LoaderCallbacks<Cursor>
{
    final static int CONTACTS_LOADER = 1;

    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader( CONTACTS_LOADER, null, this );

        mAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.two_line_list_item,
                null,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                new int[]   {android.R.id.text1, android.R.id.text2}
        );
        setListAdapter(mAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch ( id ) {
            case CONTACTS_LOADER:
                return new CursorLoader(
                             getApplicationContext(),
                             ContactsContract.Contacts.CONTENT_URI,
                             null, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch ( loader.getId() ) {
            case CONTACTS_LOADER:
                mAdapter.swapCursor ( data );
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch ( loader.getId() ) {
            case CONTACTS_LOADER:
                mAdapter.swapCursor ( null );
                break;
        }

    }
}
