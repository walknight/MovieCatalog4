package com.dtaoa.moviecatalog4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.dtaoa.moviecatalog4.db.DatabaseContract.FavoriteColumn.ID_API;
import static com.dtaoa.moviecatalog4.db.DatabaseContract.FavoriteColumn.TABLE_NAME;
import static com.dtaoa.moviecatalog4.db.DatabaseContract.FavoriteColumn.TYPE;

public class FavoriteHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;

    private FavoriteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context){
        if(INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if(INSTANCE == null){
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException{
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if(database.isOpen()){
            database.close();
        }
    }

    public Cursor getFavorite(String type){
        return database.rawQuery("Select * FROM " + TABLE_NAME + " WHERE " + TYPE + " = ? ", new String[]{type});
    }

    public boolean isFavorite(String id){

        Cursor cursor = database.rawQuery("Select * FROM " + TABLE_NAME + " WHERE " + ID_API + " = ? ", new String[]{id});
        if(cursor.getCount() > 0)
            return true;

        cursor.close();
        return false;

    }

    public static long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }


}
