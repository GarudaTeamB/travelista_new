package com.phedev.app.travelista.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.phedev.app.travelista.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by phedev in 2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "travel.db";
    private static final int DATABASE_VERSION = 1;
    //table name
    public static final String TABLE_NAME = "data";
    //column name
    public static final String KEY_ID = "key_id";
    public static final String NAME = "place_name";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE_PATH = "image";
    public static final String PROVINCE = "province";
    public static final String LOCATION = "location";
    public static final String CONTENT_TEXT_BY = "content_by";
    public static final String CONTENT_IMAGE_BY = "image_by";

    private static final String SQL_CREATE_TABLE_TASKS = String.format("CREATE TABLE %s"
                    +" (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
            TABLE_NAME, KEY_ID, NAME, DESCRIPTION, IMAGE_PATH, PROVINCE, LOCATION, CONTENT_TEXT_BY, CONTENT_IMAGE_BY
    );

    //used to read data from /assets and /res
    private Resources mResources;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TASKS);

        try {
            readDataFromResources(db);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Streams the JSON data from datawisata.json, parses it, and inserts it into the
     * provided {@link SQLiteDatabase}.
     *
     * @param db Database where objects should be inserted.
     * @throws IOException
     * @throws JSONException
     */
    @SuppressWarnings("JavaDoc")
    private void readDataFromResources(SQLiteDatabase db) throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.datawisata);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        //Parse resource into key/values
        final String rawJson = builder.toString();
        //Parse JSON data and insert into the provided database instance
        try{
            Log.d(TAG, rawJson);
            JSONObject jsonObject = new JSONObject(rawJson);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            Log.d(TAG, String.valueOf(jsonArray.length()));
            for (int i = 0; i < jsonArray.length(); i++ ){
                JSONObject object = jsonArray.getJSONObject(i);
                String placeName = object.getString("nama");
                String description = object.getString("deskripsi");
                String province = object.getString("provinsi");
                String imageAsset = object.getString("image");
                String location = object.getString("tempat");
                String contentText = object.getString("konten_teks");
                String contentImage = object.getString("konten_gambar");

                ContentValues values = new ContentValues();
                values.put(NAME, placeName);
                values.put(DESCRIPTION, description);
                values.put(PROVINCE, province);
                values.put(IMAGE_PATH, imageAsset);
                values.put(LOCATION, location);
                values.put(CONTENT_TEXT_BY, contentText);
                values.put(CONTENT_IMAGE_BY, contentImage);

                db.insertOrThrow(TABLE_NAME, null, values);
            }
        }catch (JSONException e){
            Log.d(TAG, e.getMessage());
        }

    }
}
