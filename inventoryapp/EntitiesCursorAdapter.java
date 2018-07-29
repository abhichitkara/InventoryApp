package com.example.abhi.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhi.inventoryapp.Particulars.EntitiesContract;


public class EntitiesCursorAdapter extends CursorAdapter {

    private final MainActivity main_activity;
    TextView articlenameTextView;
    TextView articlequantityTextView;
    TextView articlepriceTextView;
    ImageView article_transaction;
    ImageView article_image;

    public EntitiesCursorAdapter(MainActivity context_main_activity, Cursor c1) {
        super(context_main_activity, c1, 0);
        this.main_activity = context_main_activity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_entity, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        articlenameTextView = (TextView) view.findViewById(R.id.article_name);
        articlequantityTextView = (TextView) view.findViewById(R.id.article_quantity);
        articlepriceTextView = (TextView) view.findViewById(R.id.article_price);
        article_transaction = (ImageView) view.findViewById(R.id.article_transaction);
        article_image = (ImageView) view.findViewById(R.id.article_image_view);

        String aticle_name = cursor.getString(cursor.getColumnIndex(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_NAME));
        final int article_quantity = cursor.getInt(cursor.getColumnIndex(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_QUANTITY));
        String article_price = cursor.getString(cursor.getColumnIndex(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_PRICE));

        article_image.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndex(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_IMAGE))));

        articlenameTextView.setText(aticle_name);
        articlequantityTextView.setText(String.valueOf(article_quantity));
        articlepriceTextView.setText(article_price);

        final long article_id = cursor.getLong(cursor.getColumnIndex(EntitiesContract.EntitiesEntry._ID));

        article_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.TransactionOnClick(article_id, article_quantity);
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.onClickEntityView(article_id);
            }
        });


    }
}
