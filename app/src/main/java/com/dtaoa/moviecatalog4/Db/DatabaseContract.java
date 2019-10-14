package com.dtaoa.moviecatalog4.Db;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static final class FavoriteColumn implements BaseColumns{
        public static final String TABLE_NAME = "favorite";

        public static final String TITLE = "title";
        public static final String SINOPSIS = "sinopsis";
        public static final String GENRE = "genre";
        public static final String YEAR = "year";
        public static final String RATINGS = "ratings";
        public static final String POSTER = "poster";
        public static final String THUMBNAIL = "thumbnail";
        public static final String TYPE = "type";

    }
}
