package com.example.lenovo.onlinetrending;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Lenovo on 11-05-2018.
 */

public class NewsDatabase extends SQLiteOpenHelper {

    public static final String AUTHORITY = "com.example.lenovo.onlinetrending";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String path_Tasks = "NewsDetails";
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(path_Tasks).build();
    public static final String Databasename = "NewsDatabase";
    public static final String tablename = "NewsDetails";
    public static final int version = 1;
    public static final String id = "id";
    public static final String Email="Email";
    public static final String Title="Title";
    public static final String Description="Description";
    public static final String UrlToImage="UrlToImage";
    public static final String Url="Url";
    public static final String sourceId="sourceId";
    public static final String sourceName="sourceName";
    public static final String author="author";
    public static final String publishedAt="publishedAt";
   /* public static final String movieid = "movieid";
    public static final String movieoriginaltitle = "movieoriginaltitle";
    public static final String moviebackdroppath = "backdroppath";
    public static final String movieposterpath = "posterpath";
    public static final String movierating = "rating";
    public static final String moviereleasedate = "releasedate";
    public static final String movieoverview = "overview";*/
    public Context c;

    public NewsDatabase(Context context) {
        super(context, Databasename, null, version);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String query = "CREATE TABLE " + tablename + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + Email +" TEXT,"+Title + " TEXT, " +Description + " TEXT," +
                UrlToImage + " TEXT," + Url + " TEXT," + sourceId + " TEXT," + sourceName + " TEXT," +
                author+" TEXT,"+publishedAt + " TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tablename);
        onCreate(db);
    }
}


