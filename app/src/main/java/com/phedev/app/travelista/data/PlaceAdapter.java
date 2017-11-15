package com.phedev.app.travelista.data;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phedev.app.travelista.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by phedev in 2017.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public PlaceAdapter(Cursor cursor, Context context, OnItemClickListener onItemClickListener){
        mCursor = cursor;
        mContext = context;
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int adapterPosition);
    }

    private void postItemClick(PlaceAdapter.ViewHolder placeAdapter) {
        mOnItemClickListener.onItemClick(placeAdapter.itemView, placeAdapter.getAdapterPosition());
    }

    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, parent, false);

        return new PlaceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        //get data from database
        String name = DatabaseManager.getColumnString(mCursor, DbHelper.NAME);
        String location = DatabaseManager.getColumnString(mCursor, DbHelper.LOCATION);
        String province = DatabaseManager.getColumnString(mCursor, DbHelper.PROVINCE);
        String imagePath = DatabaseManager.getColumnString(mCursor, DbHelper.IMAGE_PATH);
        String contentBY = DatabaseManager.getColumnString(mCursor, DbHelper.CONTENT_TEXT_BY);

        //bind data to views
        holder.place.setText(name);
        holder.location.setText(province + " - " + location);
        holder.person.setText(contentBY);
        //get data from assets by path and bind data to background layout
        InputStream ims = null;
        try {
            ims = mContext.getAssets().open(imagePath);
            Drawable drawable = Drawable.createFromStream(ims, null);
            holder.image.setBackground(drawable);
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

    @Override
    public int getItemCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView place,location,person;
        RelativeLayout image;
        ViewHolder(View itemView) {
            super(itemView);
            place = (TextView) itemView.findViewById(R.id.place);
            location = (TextView) itemView.findViewById(R.id.location);
            person = (TextView) itemView.findViewById(R.id.konten_by);

            image = (RelativeLayout) itemView.findViewById(R.id.layout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mCursor.moveToPosition(pos);

            postItemClick(this);
        }
    }

    public Data getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new IllegalArgumentException("Item position is out of adapter's range");
        } else if (mCursor.moveToPosition(position)) {
            return new Data(mCursor);
        }
        return null;
    }

    public void swapCursor(Cursor cursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = cursor;
        notifyDataSetChanged();
    }
}
