package com.phedev.app.travelista;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phedev.app.travelista.data.Data;

import java.io.IOException;
import java.io.InputStream;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";
    private TextView nameTxt,locationTxt,contentByTxt,imageBytxt,descTxt;
    private RelativeLayout image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Data data = getIntent().getParcelableExtra(EXTRA_DATA);

        initViews();

        //get data from parcelable
        String name = data.name;
        String desc = data.description;
        String location = data.province + " - " + data.location;
        String imagePath = data.image;
        String contentBy = data.contentText;
        String imageBy = getString(R.string.image_text, data.contentImage);

        //bind data to views
        nameTxt.setText(name);
        descTxt.setText(desc);
        locationTxt.setText(location);
        contentByTxt.setText(contentBy);
        imageBytxt.setText(imageBy);
        //get data from assets by path and bind data to view
        InputStream ims = null;
        try {
            ims = getApplicationContext().getAssets().open(imagePath);
            Drawable drawable = Drawable.createFromStream(ims, null);
            image.setBackground(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ims!=null){
                    ims.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void initViews(){
        nameTxt = (TextView)findViewById(R.id.name_detail);
        descTxt = (TextView)findViewById(R.id.deskripsi);
        locationTxt = (TextView)findViewById(R.id.location_detail);
        image = (RelativeLayout) findViewById(R.id.image_detail);
        contentByTxt = (TextView) findViewById(R.id.konten_detail_by);
        imageBytxt = (TextView)findViewById(R.id.konten_gambar_by);
    }
}
