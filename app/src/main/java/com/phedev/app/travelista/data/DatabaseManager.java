package com.phedev.app.travelista.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by phedev in 2017.
 */

public class DatabaseManager {

    private static DatabaseManager sInstance;

    public static synchronized DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context.getApplicationContext());
        }

        return sInstance;
    }

    private DbHelper dbHelper;

    private DatabaseManager(Context context) {
        dbHelper = new DbHelper(context);
    }

    /**
     * Return a {@link Cursor} that contains every data in the database.
     *
     * @param sortOrder Optional sort order string for the query, can be null
     * @return {@link Cursor} containing all data results.
     */
    public Cursor queryAllData(String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DbHelper.TABLE_NAME, null, null, null, null, null, sortOrder);

        while (!cursor.isAfterLast()){
            cursor.moveToNext();
        }

        return cursor;
    }

    /* Helpers to retrieve column values */
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    /*sort options*/
    public static final String NAME_SORT = String.format("%s ASC",
            DbHelper.NAME);
}
