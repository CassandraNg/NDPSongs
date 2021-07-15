package sg.edu.rp.c346.id20017476.ndpsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "simplesong.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTE = "song";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "song_title";
    private static final String COLUMN_SINGER = "song_singer";
    private static final String COLUMN_YEAR = "song_year";
    private static final String COLUMN_STARS = "song_star";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT, " +
                COLUMN_SINGER + " TEXT, " +
                COLUMN_YEAR + " INTEGER, " +
                COLUMN_STARS + " INTEGER )";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE "+ TABLE_NOTE+ " ADD COLUMN module_name TEXT");

    }

    public long insertSong(String title, String singer, int year, int stars){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGER, singer);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STARS, stars);
        long result = db.insert(TABLE_NOTE, null, values);
        db.close();
        Log.d("SQL Insert", "" + result);
        if (result == -1) {
            Log.d("DBHelper", "Insert failed");
        }
        return result;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> Song = new ArrayList<Song>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGER, COLUMN_YEAR, COLUMN_STARS};
        Cursor cursor = db.query(TABLE_NOTE, columns, null ,null,
                null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String singers = cursor.getString(2);
                    int year = cursor.getInt(3);
                    int stars = cursor.getInt(4);

                    Song newSong = new Song(id, title, singers, year, stars);
                    Song.add(newSong);
                } while (cursor.moveToNext());
            }
        cursor.close();
        db.close();
        return Song;
    }

    public ArrayList<Song> getAllSongs(int filter) {
        ArrayList<Song> Song = new ArrayList<Song>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGER, COLUMN_YEAR, COLUMN_STARS};
        String condition =  COLUMN_STARS + ">= ?";
        String[] args = {(String.valueOf(filter))};
        Cursor cursor = db.query(TABLE_NOTE, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singer = cursor.getString(2);
                int year = cursor.getInt(3);
                int star = cursor.getInt(4);
                Song song = new Song(id, title, singer, year, star);
                Song.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Song;
    }

    public int updateSong(Song data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_SINGER, data.getSingers());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_NOTE, values, condition, args);
        db.close();
        return result;
    }

    public int deleteSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NOTE, condition, args);
        db.close();
        return result;
    }
}
