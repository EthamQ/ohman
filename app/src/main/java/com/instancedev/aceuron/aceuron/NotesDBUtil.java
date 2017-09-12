package com.instancedev.aceuron.aceuron;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by instance on 06/09/17.
 */

public class NotesDBUtil extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "notes.db";

    public NotesDBUtil(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TextUtil.SQL_CREATE_NOTES_TBL);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO I hope we will never have to do this
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //returns a simple Cursor object, that looks only for one title
    //if boolean standardProjection = true: you didn't change any attributes
    // of the notes ("title","content","encrypted")
    public Cursor simpleTitleCursor(boolean standardProjection, String title){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                "title",
                "content",
                "encrypted"
        };

        String selection = "title = ?";
        String[] selectionArgs = { title };

        Cursor cursor = db.query(
                "notes",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }
}
