package com.phedev.app.travelista;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.phedev.app.travelista.data.Data;
import com.phedev.app.travelista.data.DatabaseManager;
import com.phedev.app.travelista.data.PlaceAdapter;

public class MainActivity extends AppCompatActivity implements PlaceAdapter.OnItemClickListener {

    private PlaceAdapter adapter;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "this add place menu is under develop",
                        Toast.LENGTH_LONG).show();
            }
        });

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        DatabaseManager manager = DatabaseManager.getInstance(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new PlaceAdapter(mCursor, getApplicationContext(), this);
        recyclerView.setAdapter(adapter);

        mCursor = manager.queryAllData(DatabaseManager.NAME_SORT);
        adapter.swapCursor(mCursor);
    }

    @Override
    public void onItemClick(View v, int adapterPosition) {
        Intent launchDetail = new Intent(getApplicationContext(), DetailActivity.class);
        Data data = adapter.getItem(adapterPosition);
        launchDetail.putExtra(DetailActivity.EXTRA_DATA, data);
        startActivity(launchDetail);
    }
}
