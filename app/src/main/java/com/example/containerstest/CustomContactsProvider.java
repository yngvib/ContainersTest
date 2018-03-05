package com.example.containerstest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class CustomContactsProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.containerstest.CustomContactsProvider";
    private static final String BASE_PATH = "customcontacts";

    public static final Uri CUSTOM_CONTACTS_URI = Uri.parse(
      "content://" + AUTHORITY + "/" + BASE_PATH);
    public static String _ID = "_id";
    public static String DISPLAY_NAME = "name";

    private class Contact {
        Contact( int i, String n ) {
            _id = i;
            name = n;
        }
        int _id;
        String name;
    }

    private List<Contact> mData = new ArrayList<Contact>();

    private String[] mColumns = new String[] { _ID, DISPLAY_NAME };

    private int id_counter = 0;

    public CustomContactsProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        Contact contact = new Contact( ++id_counter, values.getAsString(DISPLAY_NAME) );
        mData.add( contact );
        getContext().getContentResolver().notifyChange( uri, null );
        return Uri.parse( BASE_PATH + "/" + id_counter );
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        mData.add( new Contact( ++id_counter, "Person one") );
        mData.add( new Contact( ++id_counter, "Person two") );
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        MatrixCursor cursor = new MatrixCursor( mColumns );
        for ( Contact c : mData ) {
            cursor.addRow( new Object[] { c._id, c.name} );
        }
        cursor.setNotificationUri( getContext().getContentResolver(), CUSTOM_CONTACTS_URI );
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
