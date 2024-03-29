package com.dtaoa.moviecatalog4.Helper;

import android.database.Cursor;

import com.dtaoa.moviecatalog4.ViewModel.DataModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dtaoa.moviecatalog4.db.DatabaseContract.FavoriteColumn.GENRE;
import static com.dtaoa.moviecatalog4.db.DatabaseContract.FavoriteColumn.POSTER;
import static com.dtaoa.moviecatalog4.db.DatabaseContract.FavoriteColumn.RATINGS;
import static com.dtaoa.moviecatalog4.db.DatabaseContract.FavoriteColumn.SINOPSIS;
import static com.dtaoa.moviecatalog4.db.DatabaseContract.FavoriteColumn.THUMBNAIL;
import static com.dtaoa.moviecatalog4.db.DatabaseContract.FavoriteColumn.TITLE;
import static com.dtaoa.moviecatalog4.db.DatabaseContract.FavoriteColumn.TYPE;
import static com.dtaoa.moviecatalog4.db.DatabaseContract.FavoriteColumn.YEAR;

public class MappingHelper {

    public static ArrayList<DataModel> mapCursorToArrayList(Cursor favCursor){
        ArrayList<DataModel> favoriteList = new ArrayList<>();
        while(favCursor.moveToNext()){
            int id = favCursor.getInt(favCursor.getColumnIndexOrThrow(_ID));
            String title = favCursor.getString(favCursor.getColumnIndexOrThrow(TITLE));
            String sinopsis = favCursor.getString(favCursor.getColumnIndexOrThrow(SINOPSIS));
            String genre = favCursor.getString(favCursor.getColumnIndexOrThrow(GENRE));
            String year = favCursor.getString(favCursor.getColumnIndexOrThrow(YEAR));
            String ratings = favCursor.getString(favCursor.getColumnIndexOrThrow(RATINGS));
            String imagePoster = favCursor.getString(favCursor.getColumnIndexOrThrow(POSTER));
            String imageThumbnail = favCursor.getString(favCursor.getColumnIndexOrThrow(THUMBNAIL));
            String type = favCursor.getString(favCursor.getColumnIndexOrThrow(TYPE));
            favoriteList.add(new DataModel(id, title, sinopsis, genre, year, ratings, imagePoster, imageThumbnail, type));
        }

        return favoriteList;
    }
}
