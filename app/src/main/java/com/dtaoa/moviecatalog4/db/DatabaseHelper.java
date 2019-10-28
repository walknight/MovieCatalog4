package com.dtaoa.moviecatalog4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import static com.dtaoa.moviecatalog4.db.DatabaseContract.FavoriteColumn.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbmoviecatalog";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_NAME,
            DatabaseContract.FavoriteColumn._ID,
            DatabaseContract.FavoriteColumn.ID_API,
            DatabaseContract.FavoriteColumn.TITLE,
            DatabaseContract.FavoriteColumn.SINOPSIS,
            DatabaseContract.FavoriteColumn.GENRE,
            DatabaseContract.FavoriteColumn.YEAR,
            DatabaseContract.FavoriteColumn.RATINGS,
            DatabaseContract.FavoriteColumn.POSTER,
            DatabaseContract.FavoriteColumn.THUMBNAIL,
            DatabaseContract.FavoriteColumn.TYPE);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
